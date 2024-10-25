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
    private val getProductsUseCase: GetProductUseCase
):ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()


    init {
        getProducts()
    }

    private fun getProducts(){
        viewModelScope.launch {
            getProductsUseCase.execute().let { result->
                when(result){
                    is ResultWrapper.Success -> {
                        _uiState.value = HomeScreenUIEvents.Success(result.value)
                    }
                    is ResultWrapper.Failure -> {
                        _uiState.value = HomeScreenUIEvents.Error(result.exception.message?:"An Error Occurred")
                    }
                }

            }
        }
    }

}

sealed class HomeScreenUIEvents(){
    data object Loading:HomeScreenUIEvents()
    data class Success(val data:List<Product>):HomeScreenUIEvents()
    data class Error(val message:String):HomeScreenUIEvents()
}