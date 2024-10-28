package com.example.domain.usecase

import com.example.domain.repository.CartRepository

class GetCartUseCase(
    private val repository: CartRepository

) {
    suspend fun execute() = repository.getCart()

}