package com.example.kinoteka.presentation.ui.screencompose.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinoteka.R
import com.example.kinoteka.presentation.model.GenreContent

@Composable
fun FavoritesGenres(genres: List<GenreContent>, onDeleteGenreClick: (GenreContent) -> Unit,) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.favorite_genres),
            fontSize = 20.sp,
            style = TextStyle(
                brush =
                Brush.linearGradient(
                    colors = listOf(Color(0xFFDF2800), Color(0xFFFF6633)),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
            fontFamily = FontFamily(
                Font(R.font.manrope_bold, FontWeight.Normal)
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        genres.forEach { genre ->
            GenreBlock(genre, onDeleteGenreClick)
            Spacer(modifier = Modifier.size(8.dp))
        }

    }
}