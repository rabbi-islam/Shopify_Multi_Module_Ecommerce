package com.example.shopify.di

import com.example.shopify.ui.feature.cart.CartViewModel
import com.example.shopify.ui.feature.cartSummary.CartSummaryViewModel
import com.example.shopify.ui.feature.home.HomeViewModel
import com.example.shopify.ui.feature.orders.OrdersViewModel
import com.example.shopify.ui.feature.product_details.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProductDetailsViewModel(get()) }
    viewModel { CartViewModel(get(), get(), get()) }
    viewModel { CartSummaryViewModel(get(), get()) }
    viewModel { OrdersViewModel(get())}
}