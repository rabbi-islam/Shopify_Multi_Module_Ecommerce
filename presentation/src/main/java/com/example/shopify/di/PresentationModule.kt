package com.example.shopify.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
}