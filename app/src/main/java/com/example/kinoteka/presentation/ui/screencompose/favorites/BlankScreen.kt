package com.example.kinoteka.presentation.ui.screencompose.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinoteka.R
import com.example.kinoteka.presentation.ui.screencompose.utils.GradientBackgroundHalfOverlay

@Composable
fun BlankScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.welcome_screen_bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            GradientBackgroundHalfOverlay(
                bottom = false,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            GradientBackgroundHalfOverlay(
                bottom = true,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            Text(
                text = stringResource(R.string.Favorites),
                fontSize = 24.sp,
                color = colorResource(R.color.white),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 24.dp, start = 24.dp),
                fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Normal))
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 104.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.its_empty_now),
                color = colorResource(R.color.white),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Normal))
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.add_favorites_genres_and_movies),
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.manrope_medium, FontWeight.Normal)),
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.size(24.dp))
            TextButton(
                onClick = { },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFDF2800), Color(0xFFFF6633)),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = stringResource(R.string.find_movie),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Normal)),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}