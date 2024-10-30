package com.example.shopify.ui.feature.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val loginState = _registerState

    fun login(name:String, email: String, password: String) {
        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            when (val result = registerUseCase.execute(name, email, password)) {
                is ResultWrapper.Success -> {
                    _registerState.value = RegisterState.Success
                }

                is ResultWrapper.Failure -> {
                    _registerState.value =
                        RegisterState.Error(result.exception.message ?: "Something went wrong!")
                }
            }
        }
    }
}

    sealed class RegisterState {
        data object Idle : RegisterState()
        data object Loading : RegisterState()
        data object Success : RegisterState()
        data class Error(val message: String) : RegisterState()

    }