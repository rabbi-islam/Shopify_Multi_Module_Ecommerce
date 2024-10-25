package com.example.shopify

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.shopify.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopifyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShopifyApp)
            modules(listOf(
                presentationModule,
                domainModule,
                dataModule
            ))
        }
    }
}