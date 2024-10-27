package com.example.shopify.di

import com.example.shopify.ui.feature.home.HomeViewModel
import com.example.shopify.ui.feature.product_details.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProductDetailsViewModel(get()) }
}