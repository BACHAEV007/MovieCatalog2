package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinoteka.R

@Composable
fun TitleBox(name: String, tagline: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFDF2800), Color(0xFFFF6633)),
                    start = Offset.Zero,
                    end = Offset.Infinite
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Text(text = name, color = Color.White, fontSize = 36.sp,
                fontFamily = FontFamily(
                    Font(R.font.manrope_bold, FontWeight.Normal)
                )
            )
            tagline?.takeIf { it != "-" }?.let {
                Text(text = it, color = Color.White, fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(R.font.manrope_regular, FontWeight.Normal)
                    ))
            }
        }
    }
}