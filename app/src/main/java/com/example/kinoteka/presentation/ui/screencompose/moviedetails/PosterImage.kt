package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinoteka.presentation.ui.screencompose.utils.GradientBackground

@Composable
fun PosterImage(posterUrl: String, isTitleHidden: Boolean) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Movie Poster",
        modifier = Modifier
            .fillMaxWidth()
            .height(464.dp)
            .clip(shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
        contentScale = ContentScale.Crop
    )
    if (isTitleHidden) {
        GradientBackground(false)
    }
    GradientBackground()
}