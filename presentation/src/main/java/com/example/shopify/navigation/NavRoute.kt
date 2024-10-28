package com.example.shopify.navigation

import com.example.domain.model.Product
import com.example.shopify.model.UiProductModel
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
object CartSummaryScreen

@Serializable
class ProductDetailsScreen(val product: UiProductModel)