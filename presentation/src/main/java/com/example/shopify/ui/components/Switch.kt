package com.example.shopify.ui.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.shopify.util.LanguageSwitcher
import com.example.shopify.util.LanguageSwitcher.setLocale
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun CustomSwitch(
    context: Context,
    height: Dp,
    width: Dp,
    circleButtonPadding: Dp,
    outerBackgroundOnResource: Int,
    outerBackgroundOffResource: Int,
    circleBackgroundOnResource: Int,
    circleBackgroundOffResource: Int,
    stateOn: Int,
    stateOff: Int,
    initialValue: Int,
    onCheckedChanged: (checked: Boolean) -> Unit
) {
    val swipeableState = rememberSwipeableState(
        initialValue = initialValue,
        confirmStateChange = { newState ->
            val newContext = if (newState == stateOff) {
                LanguageSwitcher.setLocale(context, "en")
                onCheckedChanged(false)
            } else {
                LanguageSwitcher.setLocale(context, "bn")
                onCheckedChanged(true)
            }

            (newContext as? Activity)?.recreate() // Restart activity with new locale
            true
        }
    )

    // Rest of the code remains the same
    val sizePx = with(LocalDensity.current) { (width - height).toPx() }
    val anchors = mapOf(0f to stateOff, sizePx to stateOn)

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(RoundedCornerShape(height))
            .border(1.dp, Color.DarkGray, CircleShape)
            .background(Color.Transparent)
            .then(
                if (swipeableState.currentValue == stateOff) Modifier.paint(
                    painterResource(id = outerBackgroundOffResource),
                    contentScale = ContentScale.FillBounds
                ) else Modifier.paint(
                    painterResource(id = outerBackgroundOnResource),
                    contentScale = ContentScale.FillBounds
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .size(height)
                .padding(circleButtonPadding)
                .clip(RoundedCornerShape(50))
                .then(
                    if (swipeableState.currentValue == stateOff) Modifier.paint(
                        painterResource(id = circleBackgroundOffResource),
                        contentScale = ContentScale.FillBounds
                    ) else Modifier.paint(
                        painterResource(id = circleBackgroundOnResource),
                        contentScale = ContentScale.FillBounds
                    )
                )
                .clickable {
                    scope.launch {
                        if (swipeableState.currentValue == stateOff) {
                            swipeableState.animateTo(stateOn)
                        } else {
                            swipeableState.animateTo(stateOff)
                        }
                    }
                }
        )
    }
}
