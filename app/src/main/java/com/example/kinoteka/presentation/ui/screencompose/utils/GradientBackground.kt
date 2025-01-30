package com.example.kinoteka.presentation.ui.screencompose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GradientBackground(bottom: Boolean = true) {
    val gradientColors = if (bottom) {
        listOf(Color(0x001A1A1A), Color(0xFF1A1A1A))
    } else {
        listOf(Color(0xFF1E1E1E), Color(0x001E1E1E))
    }

    val gradientBrush = Brush.verticalGradient(
        colors = gradientColors,
        startY = 0f,
        endY = 400f
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (bottom) 304.dp else 0.dp)
            .height(160.dp)
            .background(gradientBrush)
    )
}