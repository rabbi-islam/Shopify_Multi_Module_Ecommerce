package com.example.shopify.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import java.util.Locale

object LanguageSwitcher {
    fun setLocale(context: Context, language: String): ContextWrapper {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        val updatedContext = context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        return ContextWrapper(updatedContext)
    }
}