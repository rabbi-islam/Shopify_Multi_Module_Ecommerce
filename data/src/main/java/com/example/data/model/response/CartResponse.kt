package com.example.data.model.response

import com.example.domain.model.CartModel
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val data: List<CartItem>,
    val msg: String
){
    fun toCartModel(): CartModel{
        return CartModel(
            data = data.map { it.toCartItemModel() },
            msg = msg
        )
    }
}
