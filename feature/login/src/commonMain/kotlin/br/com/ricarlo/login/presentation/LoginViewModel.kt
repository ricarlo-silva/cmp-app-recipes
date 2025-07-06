package br.com.ricarlo.login.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.login.domain.repository.AuthRepository
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
    val permissionsController: PermissionsController,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        requestPermission(Permission.REMOTE_NOTIFICATION)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.UsernameChanged -> onUsernameChange(action.username)
            is LoginAction.PasswordChanged -> onPasswordChange(action.password)
            LoginAction.LoginClicked -> onLoginClick()
            LoginAction.ForgotPasswordClicked -> onForgotPasswordClick()
            LoginAction.GoogleLoginClicked -> onGoogleLoginClick()
            LoginAction.SignupClicked -> onSignupClick()
            LoginAction.TogglePasswordVisibility -> togglePasswordVisibility()
        }
    }

    private fun onUsernameChange(username: String) {
        _state.update { it.withUsername(username) }
    }

    private fun onPasswordChange(password: String) {
        _state.update { it.withPassword(password) }
    }

    private fun togglePasswordVisibility() {
        _state.update { it.toggledPasswordVisibility() }
    }

    private fun onLoginClick() {
        _state.update { it.loading(true) }
        viewModelScope.launch {
            runCatching {
                authRepository.login(
                    username = state.value.username,
                    password = state.value.password
                )
            }.onSuccess {
                _state.update { it.loading(false) }
                _sideEffect.emit(LoginSideEffect.Navigate(route = "home"))
            }.onFailure { error ->
                _state.update { it.loading(false) }
                _sideEffect.emit(LoginSideEffect.ShowSnackbar("Error: ${error.message}"))
            }
        }
    }

    private fun onSignupClick() {
        viewModelScope.launch {
            // TODO: Navigate to signup screen
            _sideEffect.emit(LoginSideEffect.ShowSnackbar("Navigate to signup screen"))
        }
    }

    private fun onForgotPasswordClick() {
        viewModelScope.launch {
            // TODO: Navigate to forgot password screen
            _sideEffect.emit(LoginSideEffect.ShowSnackbar("Navigate to forgot password screen"))
        }
    }

    private fun onGoogleLoginClick() {
        viewModelScope.launch {
            // TODO: Handle Google login
            _sideEffect.emit(LoginSideEffect.ShowSnackbar("Handle Google login"))
        }
    }

    private fun requestPermission(permission: Permission) {
        viewModelScope.launch {
            try {
                permissionsController.getPermissionState(permission)
                    .also { _sideEffect.emit(LoginSideEffect.ShowSnackbar("pre provide $it")) }

                permissionsController.providePermission(permission)
            } catch (deniedAlwaysException: DeniedAlwaysException) {
                _sideEffect.emit(LoginSideEffect.ShowSnackbar("onDeniedAlways: ${deniedAlwaysException.message}"))
            } catch (deniedException: DeniedException) {
                _sideEffect.emit(LoginSideEffect.ShowSnackbar("onDenied: ${deniedException.message}"))
            }
        }
    }
}

@Stable
data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordVisible: Boolean = false,
) {
    fun withUsername(username: String) = copy(username = username)
    fun withPassword(password: String) = copy(password = password)
    fun loading(isLoading: Boolean) = copy(isLoading = isLoading)
    fun toggledPasswordVisibility() = copy(passwordVisible = !passwordVisible)

    val isLoginButtonEnabled: Boolean
        get() = username.isNotBlank() && password.isNotBlank()
}

sealed class LoginAction {
    data class UsernameChanged(val username: String) : LoginAction()
    data class PasswordChanged(val password: String) : LoginAction()
    data object LoginClicked : LoginAction()
    data object SignupClicked : LoginAction()
    data object ForgotPasswordClicked : LoginAction()
    data object GoogleLoginClicked : LoginAction()
    data object TogglePasswordVisibility : LoginAction()
}

sealed class LoginSideEffect {
    data class ShowSnackbar(val message: String) : LoginSideEffect()
    data class Navigate(val route: String) : LoginSideEffect()
}
