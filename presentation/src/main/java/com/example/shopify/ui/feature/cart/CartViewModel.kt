package com.example.shopify.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CartItemModel
import com.example.domain.usecase.GetCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val useCase: GetCartUseCase):ViewModel() {

    private val _uiState = MutableStateFlow<CartEvent>(CartEvent.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCart()
    }

     fun getCart(){

        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            useCase.execute().let {result->
                when(result){
                    is com.example.domain.network.ResultWrapper.Success-> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }
                    is com.example.domain.network.ResultWrapper.Failure-> {
                        _uiState.value = CartEvent.Error("Something wen wrong!")
                    }
                }
            }


        }
    }


}

sealed class CartEvent {
    data object Loading : CartEvent()
    data class Success(val message:List<CartItemModel>) : CartEvent()
    data class Error(val message: String) : CartEvent()


}