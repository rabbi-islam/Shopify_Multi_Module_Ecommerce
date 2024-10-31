package com.example.shopify.ui.feature.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.request.AddCartRequestModel
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.AddToCartUseCase
import com.example.shopify.ShopperSession
import com.example.shopify.model.UiProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val useCase: AddToCartUseCase,
) : ViewModel() {

    val userDomainModel = ShopperSession.getUser()

    private val _state = MutableStateFlow<ProductDetailsEvent>(ProductDetailsEvent.Nothing)
    val state = _state.asStateFlow()

    fun addProductToCart(product: UiProductModel) {
        viewModelScope.launch {
            _state.value = ProductDetailsEvent.Loading
            val result = useCase.execute(
                AddCartRequestModel(
                    productId = product.id,
                    productName = product.title,
                    price = product.price,
                    quantity = 1,
                    userId = userDomainModel!!.id!!
                ),
                userDomainModel.id!!.toLong()
            )
            when(result){
                is ResultWrapper.Success -> {
                    _state.value = ProductDetailsEvent.Success("Product added to cart.")
                }
                is ResultWrapper.Failure -> {
                    _state.value = ProductDetailsEvent.Error("Something went wrong.")
                }
            }
        }

    }

}


sealed class ProductDetailsEvent {
    data object Loading : ProductDetailsEvent()
    data object Nothing : ProductDetailsEvent()
    data class Success(val message: String) : ProductDetailsEvent()
    data class Error(val message: String) : ProductDetailsEvent()
}