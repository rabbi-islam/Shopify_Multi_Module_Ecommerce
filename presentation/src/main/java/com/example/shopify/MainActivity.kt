package com.example.shopify

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shopify.model.UiProductModel
import com.example.shopify.model.UserAddressRouteWrapper
import com.example.shopify.navigation.CartScreen
import com.example.shopify.navigation.CartSummaryScreen
import com.example.shopify.navigation.HomeScreen
import com.example.shopify.navigation.LoginScreen
import com.example.shopify.navigation.OrderScreen
import com.example.shopify.navigation.ProductDetailsScreen
import com.example.shopify.navigation.ProfileScreen
import com.example.shopify.navigation.RegisterScreen
import com.example.shopify.navigation.UserAddressRoute
import com.example.shopify.navigation.productNavType
import com.example.shopify.navigation.userAddressNavType
import com.example.shopify.ui.feature.authentication.login.LoginScreen
import com.example.shopify.ui.feature.authentication.register.RegisterScreen
import com.example.shopify.ui.feature.cart.CartScreen
import com.example.shopify.ui.feature.cartSummary.CartSummaryScreen
import com.example.shopify.ui.feature.home.HomeScreen
import com.example.shopify.ui.feature.orders.OrdersScreen
import com.example.shopify.ui.feature.product_details.ProductDetailsScreen
import com.example.shopify.ui.feature.user_address.UserAddressScreen
import com.example.shopify.ui.theme.ShopifyTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopifyTheme {
                val shouldShowBottomNav = remember {
                    mutableStateOf(true)
                }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(visible = shouldShowBottomNav.value, enter = fadeIn()) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {


                        NavHost(navController = navController, startDestination = if (ShopperSession.getUser() != null) HomeScreen else LoginScreen) {

                            composable<LoginScreen> {
                                shouldShowBottomNav.value = false
                                LoginScreen(navController)
                            }

                            composable<RegisterScreen> {
                                shouldShowBottomNav.value = false
                                RegisterScreen(navController)
                            }

                            composable<HomeScreen> {
                                shouldShowBottomNav.value = true
                                HomeScreen(navController)
                            }
                            composable<CartScreen> {
                                shouldShowBottomNav.value = true
                                CartScreen(navController = navController)
                            }
                            composable<OrderScreen> {
                                shouldShowBottomNav.value = true
                                OrdersScreen()

                            }
                            composable<ProfileScreen> {
                                shouldShowBottomNav.value = true
                                Text(text = "Profile")
                            }
                            composable<ProductDetailsScreen>(
                                typeMap = mapOf(typeOf<UiProductModel>() to productNavType)
                            ) {
                                shouldShowBottomNav.value = false
                                val product = it.toRoute<ProductDetailsScreen>()
                                ProductDetailsScreen(
                                    navController = navController,
                                    product = product.product
                                )
                            }
                            composable<CartSummaryScreen> {
                                shouldShowBottomNav.value = false
                                CartSummaryScreen(navController = navController)
                            }

                            composable<UserAddressRoute>(
                                typeMap = mapOf(typeOf<UserAddressRouteWrapper>() to userAddressNavType)
                            ) {
                                shouldShowBottomNav.value = false
                                val address = it.toRoute<UserAddressRoute>()
                                UserAddressScreen(navController = navController, userAddress =address.userAddressWrapper.userAddress )
                            }

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        NavigationBar {
            //current route
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val items = listOf(
                BottomNavItems.Home,
                BottomNavItems.Order,
                BottomNavItems.Profile
            )

            items.forEach { item ->
                val isSelected =
                    currentRoute?.substringBefore("?") == item.route::class.qualifiedName
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { startRoute ->
                                popUpTo(startRoute) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text(text = item.title) },
                    icon = {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                        )
                    }, colors = NavigationBarItemDefaults.colors().copy(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = Color.Gray,
                        unselectedIconColor = Color.Gray
                    )
                )
            }
        }
    }
}

sealed class BottomNavItems(val route: Any, val title: String, val icon: Int) {
    data object Home : BottomNavItems(HomeScreen, "Home", icon = R.drawable.ic_home)
    data object Order : BottomNavItems(OrderScreen, "Order", icon = R.drawable.ic_orders)
    data object Profile : BottomNavItems(ProfileScreen, "Profile", icon = R.drawable.ic_profile_bn)
}


