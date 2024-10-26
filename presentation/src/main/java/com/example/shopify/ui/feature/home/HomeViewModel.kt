package com.example.shopify.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }


    private fun getAllProducts(){
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading
            val featuredProducts = getProducts("electronics")
            val popularProducts = getProducts("jewelery")
            if (featuredProducts.isEmpty() || popularProducts.isEmpty()) {
                _uiState.value = HomeScreenUIEvents.Error("No products found")
                return@launch
            }
            _uiState.value = HomeScreenUIEvents.Success(featuredProducts, popularProducts)
        }
    }


    private suspend fun getProducts(category: String): List<Product> {
        getProductsUseCase.execute(category).let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    return (result).value
                }

                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }
    }


}

sealed class HomeScreenUIEvents() {
    data object Loading : HomeScreenUIEvents()
    data class Success(val featured: List<Product>, val popularProducts: List<Product>) :
        HomeScreenUIEvents()

    data class Error(val message: String) : HomeScreenUIEvents()
}