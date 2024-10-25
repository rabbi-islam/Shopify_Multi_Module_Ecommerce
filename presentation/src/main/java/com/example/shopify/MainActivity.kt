package com.example.shopify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopify.ui.feature.home.HomeScreen
import com.example.shopify.ui.theme.ShopifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopifyTheme {
             val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {

                    composable("home") {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
}

