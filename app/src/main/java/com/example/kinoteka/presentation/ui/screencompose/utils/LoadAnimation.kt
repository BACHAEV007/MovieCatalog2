package com.example.kinoteka.presentation.ui.screencompose.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("download_anim.json"))
    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center),
            iterations = LottieConstants.IterateForever
        )
    }
}