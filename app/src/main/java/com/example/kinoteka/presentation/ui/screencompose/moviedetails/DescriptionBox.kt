package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinoteka.R

@Composable
fun DescriptionBox(description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.not_selected_button),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = description,
            color = Color.White,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(
                Font(R.font.manrope_medium, FontWeight.Normal)
            )
        )
    }
}