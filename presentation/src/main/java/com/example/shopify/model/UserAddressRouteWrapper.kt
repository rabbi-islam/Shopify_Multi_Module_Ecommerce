package com.example.shopify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserAddressRouteWrapper(
    val userAddress: UserAddress?,
):Parcelable
