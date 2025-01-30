package com.example.kinoteka.presentation.ui.screencompose.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kinoteka.R
import com.example.kinoteka.presentation.model.FavouriteContent

@Composable
fun FavoriteMovies(movies: List<FavouriteContent>,
                   onMovieClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            text = stringResource(R.string.favorites_movies),
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.heightIn(max = 1000.dp)
        ) {
            items(movies) { movie ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMovieClick(movie.id) }
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.7f),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        AsyncImage(
                            model = movie.poster,
                            contentDescription = "Movie Poster",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    val backgroundColor = when {
                        movie.rating.toFloat() < 2 -> colorResource(R.color.marks_one)
                        movie.rating.toFloat() < 3 -> colorResource(R.color.marks_two)
                        movie.rating.toFloat() < 4 -> colorResource(R.color.marks_three)
                        movie.rating.toFloat() < 5 -> colorResource(R.color.marks_four)
                        movie.rating.toFloat() < 6 -> colorResource(R.color.marks_five)
                        movie.rating.toFloat() < 7 -> colorResource(R.color.marks_six)
                        movie.rating.toFloat() < 8 -> colorResource(R.color.marks_seven)
                        movie.rating.toFloat() < 9 -> colorResource(R.color.marks_eight)
                        else -> colorResource(R.color.marks_nine)
                    }
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ){
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .background(
                                    color = backgroundColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                text = movie.rating.toString(),
                                color = colorResource(R.color.white),
                                fontSize = 12.sp,
                            )
                        }
                    }
                }

            }
        }
    }
}