package com.example.kinoteka.presentation

import android.app.Activity
import android.graphics.drawable.shapes.RoundRectShape
import android.util.Log
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import com.example.kinoteka.R
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.FavouritesApiService
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.presentation.factory.MovieDetailsViewModelFactory
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel
import okhttp3.internal.addHeaderLenient
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun MoviesDetailsScreen(viewModel: MovieDetailsViewModel, activity: Activity) {
    val movieDetails by viewModel.movieDetails
    val isFavourite by viewModel.isFavorite
    val movieRating by viewModel.movieRating
    val authorAvatar by viewModel.authorContent
    val reviews = movieDetails?.reviews ?: emptyList()
    var currentReviewIndex by remember { mutableStateOf(0) }
    val currentReview = reviews.getOrNull(currentReviewIndex)

    movieDetails?.let { details ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.app_background))
        ) {
            AsyncImage(

                model = ImageRequest.Builder(LocalContext.current)
                    .data(details.poster)
                    .crossfade(true)
                    .build(),
                contentDescription = "Translated description of what the image contains",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(464.dp)
                    .clip(shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { activity.finish() },
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(width = 40.dp, height = 40.dp)
                        .background(color = colorResource(R.color.not_selected_button))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_ic_full),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(Modifier.size(16.dp))
                Text(
                    text = "Название фильма",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        if (isFavourite) {
                            viewModel.removeFromFavorites(details.id)
                        } else {
                            viewModel.addToFavorites(details.id)
                        }
                    },
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(width = 40.dp, height = 40.dp)
                        .background(
                            color = if (isFavourite) colorResource(R.color.average_gradient_color)
                            else colorResource(R.color.not_selected_button)
                        )
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavourite) R.drawable.liked_ic_fill
                            else R.drawable.liked_ic
                        ),
                        contentDescription = "Favorite",
                        tint = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 96.dp)
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                Spacer(Modifier.height(359.dp))
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
                        Text(
                            text = "${details.name}",
                            color = Color.White,
                            fontSize = 36.sp
                        )
                        if (details.tagline != "-") {
                            Text(
                                text = details.tagline,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(R.color.not_selected_button),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.avatar_image),
                            contentDescription = "avatar_1"
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "нравится 3 вашим друзьям",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                }
                if (details.description != "-") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = colorResource(R.color.not_selected_button),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${details.description}",
                                color = Color.White,
                                fontSize = 16.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
                                        text = "9.9",
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
                Spacer(modifier = Modifier.size(16.dp))
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
                                painter = painterResource(R.drawable.info_ic),
                                contentDescription = "avatar_1"
                            )
                            Text(
                                text = "Информация",
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
                                    .weight(2f)
                            ) {
                                Column {
                                    Text(
                                        text = "Страны",
                                        color = colorResource(R.color.gray),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${details.country}",
                                        fontSize = 16.sp,
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
                                Column {
                                    Text(
                                        text = "Возраст",
                                        color = colorResource(R.color.gray),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${details.ageLimit}+",
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }
                            }

                        }
                        Spacer(modifier = Modifier.height(8.dp))
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
                                    .weight(1f)
                            ) {
                                Column {
                                    Text(
                                        text = "Время",
                                        color = colorResource(R.color.gray),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${details.time}",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        lineHeight = 20.sp
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
                                Column {
                                    Text(
                                        text = "Год выхода",
                                        color = colorResource(R.color.gray),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${details.year}",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        lineHeight = 20.sp
                                    )
                                }
                            }

                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
                                painter = painterResource(R.drawable.author_ic),
                                contentDescription = "avatar_1"
                            )
                            Text(
                                text = "Режиссёр",
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(

                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(authorAvatar!!.avatar)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Translated description of what the image contains",
                                        modifier = Modifier
                                            .size(width = 48.dp, height = 48.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        text = "${details.director}",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
                                painter = painterResource(R.drawable.genre_ic),
                                contentDescription = "avatar_1"
                            )
                            Text(
                                text = "Жанры",
                                color = colorResource(R.color.gray),
                                fontSize = 16.sp,
                                lineHeight = 20.sp
                            )

                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(details.genres) { genre ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = colorResource(R.color.app_background),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = genre.name,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
                                painter = painterResource(R.drawable.money_ic),
                                contentDescription = "avatar_1"
                            )
                            Text(
                                text = "Финансы",
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
                                    .weight(1f)
                            ) {
                                Column {
                                    Text(
                                        text = "Бюджет",
                                        color = colorResource(R.color.gray),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "$ ${details.budget}",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        lineHeight = 20.sp
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
                                Column {
                                    Text(
                                        text = "Сборы в мире",
                                        color = colorResource(R.color.gray),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "$ ${details.fees}",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        lineHeight = 20.sp
                                    )
                                }
                            }

                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 48.dp)
                        .fillMaxWidth()
                        .background(
                            color = colorResource(R.color.not_selected_button),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    if (currentReview != null) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.review_icon),
                                    contentDescription = "avatar_1"
                                )
                                Text(
                                    text = "Отзывы",
                                    color = colorResource(R.color.gray),
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    )
                                    .background(
                                        color = colorResource(R.color.app_background),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.avatar_image),
                                            contentDescription = "avatar_1",
                                            Modifier.size(width = 32.dp, height = 32.dp)
                                        )
                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    start = 8.dp,
                                                    end = 8.dp
                                                )
                                                .fillMaxWidth(0.77f)
                                        ) {
                                            Text(
                                                text = currentReview?.author?.nickName ?: "Анонимный пользователь",
                                                fontSize = 12.sp,
                                                lineHeight = 14.4.sp,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "${currentReview?.createDateTime}",
                                                fontSize = 12.sp,
                                                lineHeight = 14.4.sp,
                                                color = colorResource(R.color.gray_faded)
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(colorResource(R.color.marks_nine))
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.review_star_ic),
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.padding(
                                                    start = 8.dp,
                                                    top = 6.dp,
                                                    bottom = 6.dp,
                                                    end = 4.dp
                                                )
                                            )
                                            Text(
                                                text = "${currentReview?.rating}",
                                                fontSize = 16.sp,
                                                lineHeight = 20.sp,
                                                color = Color.White,
                                                modifier = Modifier
                                                    .padding(
                                                        top = 4.dp,
                                                        bottom = 4.dp,
                                                        end = 8.dp
                                                    )
                                            )
                                        }
                                    }
                                    Text(
                                        currentReview?.reviewText ?: "",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(
                                                top = 8.dp
                                            )
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    )
                                    .fillMaxWidth()
                            ) {
                                TextButton(
                                    onClick = {},
                                    modifier = Modifier
                                        .padding(end = 24.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(Color(0xFFDF2800), Color(0xFFFF6633)),
                                                start = Offset.Zero,
                                                end = Offset.Infinite
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .weight(3f)
                                ) {
                                    Text(
                                        text = "Добавить отзыв",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        if (currentReviewIndex > 0) {
                                            currentReviewIndex -= 1
                                        }
                                    },
                                    enabled = currentReviewIndex > 0,
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (currentReviewIndex > 0) colorResource(R.color.app_background) else colorResource(R.color.not_selected_button))

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.back_ic),
                                        contentDescription = null,
                                        tint = if (currentReviewIndex > 0) Color.White else Color.Gray,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        if (currentReviewIndex < reviews.size - 1) {
                                            currentReviewIndex += 1
                                        }
                                    },
                                    enabled = currentReviewIndex < reviews.size - 1,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (currentReviewIndex < reviews.size - 1) colorResource(R.color.app_background) else colorResource(R.color.not_selected_button))

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.forward_ic),
                                        contentDescription = null,
                                        tint = if (currentReviewIndex < reviews.size - 1) Color.White else Color.Gray,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }

                            }
                        }
                    } else {
                        Text(text = "Нет доступных отзывов.")
                    }

                }
            }
        }
    }
}






