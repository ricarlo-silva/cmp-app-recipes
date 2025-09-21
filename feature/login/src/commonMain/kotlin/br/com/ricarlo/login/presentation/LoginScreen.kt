package br.com.ricarlo.login.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import br.com.ricarlo.designsystem.generated.resources.google
import br.com.ricarlo.designsystem.generated.resources.visibility_off
import br.com.ricarlo.designsystem.generated.resources.visibility_on
import br.com.ricarlo.designsystem.spacing
import br.com.ricarlo.designsystem.stroke
import br.com.ricarlo.login.generated.resources.Res
import br.com.ricarlo.login.generated.resources.create_account
import br.com.ricarlo.login.generated.resources.forgot_password
import br.com.ricarlo.login.generated.resources.google_login
import br.com.ricarlo.login.generated.resources.hide_password
import br.com.ricarlo.login.generated.resources.login
import br.com.ricarlo.login.generated.resources.no_account
import br.com.ricarlo.login.generated.resources.other_access
import br.com.ricarlo.login.generated.resources.password
import br.com.ricarlo.login.generated.resources.show_password
import br.com.ricarlo.login.generated.resources.type_email
import br.com.ricarlo.login.generated.resources.type_password
import br.com.ricarlo.login.generated.resources.username
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import br.com.ricarlo.designsystem.generated.resources.Res as DesignSystemRes

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val factory = rememberPermissionsControllerFactory()

    val viewModel = koinViewModel<LoginViewModel> {
        parametersOf(factory.createPermissionsController())
    }

    BindEffect(viewModel.permissionsController)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, navController) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is LoginSideEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is LoginSideEffect.Navigate -> {
                    navController.navigate(effect.route, navOptions {
                        popUpTo("login") {
                            inclusive = true
                        }
                    })
                }
            }
        }
    }

    LoginContent(
        state = state,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit = { },
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
) {
    val isLoginButtonEnabled by remember(state.username, state.password) {
        derivedStateOf { state.isLoginButtonEnabled }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.welcomeMessage,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = MaterialTheme.spacing.large),
        ) {
            Text(
                text = stringResource(Res.string.username),
                style = MaterialTheme.typography.bodyMedium,
            )
            OutlinedTextField(
                value = state.username,
                onValueChange = {
                    onAction(LoginAction.UsernameChanged(it))
                },
                singleLine = true,
                enabled = !state.isLoading,
                placeholder = { Text(text = stringResource(Res.string.type_email)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            Text(
                text = stringResource(Res.string.password),
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    onAction(LoginAction.PasswordChanged(it))
                },
                placeholder = { Text(text = stringResource(Res.string.type_password)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (state.passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    val image = if (state.passwordVisible) {
                        DesignSystemRes.drawable.visibility_on
                    } else {
                        DesignSystemRes.drawable.visibility_off
                    }
                    val description =
                        if (state.passwordVisible) Res.string.hide_password else Res.string.show_password

                    IconButton(
                        onClick = { onAction(LoginAction.TogglePasswordVisibility) }
                    ) {
                        Icon(
                            painter = painterResource(image),
                            contentDescription = stringResource(description),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                singleLine = true,
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

            TextButton(
                onClick = { onAction(LoginAction.ForgotPasswordClicked) },
                enabled = !state.isLoading,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(Res.string.forgot_password))
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            Button(
                onClick = { onAction(LoginAction.LoginClicked) },
                enabled = isLoginButtonEnabled && !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.extraLarge,
            ) {
                if (!state.isLoading) {
                    Text(text = stringResource(Res.string.login))
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = MaterialTheme.stroke.small
                    )
                }
            }

            if (state.googleLoginEnabled) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                DividerWithText(
                    text = stringResource(Res.string.other_access),
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                OutlinedButton(
                    onClick = {
                        onAction(LoginAction.GoogleLoginClicked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.small,
                    enabled = !state.isLoading,
                    border = BorderStroke(
                        width = MaterialTheme.stroke.extraSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                    ),
                ) {
                    Icon(
                        painter = painterResource(DesignSystemRes.drawable.google),
                        contentDescription = null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    Text(text = stringResource(Res.string.google_login))
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            DividerWithText(
                text = stringResource(Res.string.no_account),
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            OutlinedButton(
                onClick = {
                    onAction(LoginAction.SignupClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !state.isLoading,
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.1f
                    )
                ),
                border = BorderStroke(
                    width = MaterialTheme.stroke.extraSmall,
                    color = MaterialTheme.colorScheme.primary
                ),
            ) {
                Text(text = stringResource(Res.string.create_account))
            }
        }
    }
}

@Composable
internal fun DividerWithText(text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = MaterialTheme.stroke.extraSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = MaterialTheme.stroke.extraSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        )
    }
}
