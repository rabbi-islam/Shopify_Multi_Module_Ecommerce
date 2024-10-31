package com.example.domain.usecase

import com.example.domain.model.AddressDomainModel
import com.example.domain.repository.OrderRepository

class PlaceOrderUseCase(
    private val orderRepository: OrderRepository
) {
    suspend fun execute(addressDomainModel: AddressDomainModel, userId: Long) =
        orderRepository.placeOrder(addressDomainModel, userId)
}