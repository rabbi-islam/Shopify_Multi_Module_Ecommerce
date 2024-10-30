package com.example.shopify.ui.feature.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.LoginUseCase
import com.example.shopify.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val result = loginUseCase.execute(email, password)
            when (result) {
                is ResultWrapper.Success -> {
                    ShopperSession.storeUser(result.value)
                    _loginState.value = LoginState.Success
                }

                is ResultWrapper.Failure -> {
                    _loginState.value =
                        LoginState.Error(result.exception.message ?: "Something went wrong!")
                }
            }
        }
    }
}

    sealed class LoginState {
        data object Idle : LoginState()
        data object Loading : LoginState()
        data object Success : LoginState()
        data class Error(val message: String) : LoginState()

    }