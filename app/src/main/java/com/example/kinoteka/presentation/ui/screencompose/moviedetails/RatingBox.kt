package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinoteka.R
import com.example.kinoteka.presentation.model.MovieRatingContent

@Composable
fun RatingBox(movieRating: MovieRatingContent?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.not_selected_button),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ranking_ic),
                    contentDescription = "avatar_1"
                )
                Text(
                    text = "Рейтинг",
                    color = colorResource(R.color.gray),
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )

            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = colorResource(R.color.app_background),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .weight(1.5f)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.app_logo),
                            contentDescription = "filmus",
                            Modifier.size(width = 40.dp, height = 20.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = movieRating?.ratingMoviesKatalog.toString(),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = colorResource(R.color.app_background),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .weight(1f)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.kinopoisk_logo),
                            contentDescription = "filmus"
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "${movieRating!!.ratingKinopoisk}",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = colorResource(R.color.app_background),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .weight(1f)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.imdb_logo),
                            contentDescription = "filmus"
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "${movieRating!!.ratingImdb}",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }

            }
        }
    }
}