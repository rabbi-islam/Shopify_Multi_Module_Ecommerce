package com.example.shopify.navigation

import com.example.shopify.model.UiProductModel
import com.example.shopify.model.UserAddressRouteWrapper
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object OrderScreen

@Serializable
object ProfileScreen

@Serializable
object CartSummaryScreen

@Serializable
class ProductDetailsScreen(val product: UiProductModel)

@Serializable
class UserAddressRoute(val userAddressWrapper: UserAddressRouteWrapper)