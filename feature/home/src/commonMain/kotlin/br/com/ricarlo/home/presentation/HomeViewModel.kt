package br.com.ricarlo.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.home.domain.repository.HomeRepository
import br.com.ricarlo.network.utils.toJson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _state = MutableStateFlow("Loading...")
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                homeRepository.getFruits()
            }.onSuccess { response ->
                _state.update { response.toJson().orEmpty() }
            }.onFailure { error ->
                _state.update { error.message.orEmpty() }
            }
        }
    }
}
