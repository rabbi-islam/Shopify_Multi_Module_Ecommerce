package com.example.shopify.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CartItemModel
import com.example.domain.usecase.DeleteProductUseCase
import com.example.domain.usecase.GetCartUseCase
import com.example.domain.usecase.UpdateQuantityUseCase
import com.example.shopify.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val useCase: GetCartUseCase,
    private val updateQuantityUseCase: UpdateQuantityUseCase,
    private val deleteItem: DeleteProductUseCase,
) : ViewModel() {

    val userDomainModel = ShopperSession.getUser()

    private val _uiState = MutableStateFlow<CartEvent>(CartEvent.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCart()
    }

    fun getCart() {

        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            useCase.execute(userDomainModel!!.id!!.toLong()).let { result ->
                when (result) {
                    is com.example.domain.network.ResultWrapper.Success -> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }

                    is com.example.domain.network.ResultWrapper.Failure -> {
                        _uiState.value = CartEvent.Error("Something wen wrong!")
                    }
                }
            }


        }
    }

    fun incrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 10) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity + 1))
    }

    fun decrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 1) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity - 1))
    }

    private fun updateQuantity(item: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            updateQuantityUseCase.execute(item, userDomainModel!!.id!!.toLong()).let { result ->
                when (result) {
                    is com.example.domain.network.ResultWrapper.Success -> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }

                    is com.example.domain.network.ResultWrapper.Failure -> {
                        _uiState.value = CartEvent.Error("Something wen wrong!")
                    }
                }
            }
        }
    }

    fun removeItem(item: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            deleteItem.execute(item.id, 1).let { result ->
                when (result) {
                    is com.example.domain.network.ResultWrapper.Success -> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }

                    is com.example.domain.network.ResultWrapper.Failure -> {
                        _uiState.value = CartEvent.Error("Something wen wrong!")
                    }
                }
            }
        }
    }

}

sealed class CartEvent {
    data object Loading : CartEvent()
    data class Success(val message: List<CartItemModel>) : CartEvent()
    data class Error(val message: String) : CartEvent()


}