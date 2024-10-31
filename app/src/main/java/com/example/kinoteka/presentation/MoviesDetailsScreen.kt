package com.example.kinoteka.presentation

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
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

@Preview
@Composable
fun MoviesDetailsScreen(/*movieId: String*/) {
    val context = LocalContext.current
    val tokenDataSource = remember { TokenDataSource(context) }
    val networkMapper = remember { NetworkMapper() }
    val movieApiService = remember { RetrofitApiClient.createMovieApi(tokenDataSource) }
    val favouritesApiService = remember { RetrofitApiClient.createFavouritesApi(tokenDataSource) }
    val contentMapper = remember { MoviesMapper() }

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val movieDetailsViewModel: MovieDetailsViewModel? = viewModelStoreOwner?.let {
        val viewModelFactory = remember {
            MovieDetailsViewModelFactory(
                favouritesApiService,
                movieApiService,
                networkMapper,
                contentMapper
            )
        }
        viewModel(it, factory = viewModelFactory)
    }
    movieDetailsViewModel?.loadMovieDetails("82c34463-daf4-4702-a2b8-08d9b9f3d2a2")
    val movieDetails = movieDetailsViewModel?.movieDetails?.value
//    movieDetails?.let { details ->
    // Теперь можно получить доступ ко всем полям details напрямую
    Box(modifier = Modifier
        .fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.example),
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .fillMaxWidth()
                .height(464.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                //.background(colorResource(R.color.app_background))
                .padding(horizontal = 24.dp)
        ) {
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
                    .padding(16.dp) // Добавляем padding, чтобы текст не прилипал к краям
            ) {
                Column {
                    Text(
                        text = "1889",
                        color = Color.White,
                        fontSize = 36.sp
                    )
                    Text(
                        text = "What is lost will be found",
                        color = Color.White,
                        fontSize = 20.sp
                    )
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
                    .padding(16.dp) // Добавляем padding, чтобы текст не прилипал к краям
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
            Spacer(modifier = Modifier.height(16.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        color = colorResource(R.color.not_selected_button),
//                        shape = RoundedCornerShape(8.dp)
//                    )
//                    .padding(16.dp) // Добавляем padding, чтобы текст не прилипал к краям
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.Center),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Группа европейских мигрантов покидает Лондон на пароходе, чтобы начать новую жизнь в Нью-Йорке. Когда они сталкиваются с другим судном, плывущим по течению в открытом море, их путешествие превращается в кошмар",
//                        color = Color.White,
//                        fontSize = 16.sp,
//                        lineHeight = 20.sp
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(R.color.not_selected_button),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp) // Добавляем padding, чтобы текст не прилипал к краям
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
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                                Image(painter = painterResource(R.drawable.app_logo),
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
                        Box(modifier = Modifier
                            .background(
                                color = colorResource(R.color.app_background),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .weight(1f)
                        ) {
                            Row {
                                Image(painter = painterResource(R.drawable.kinopoisk_logo),
                                    contentDescription = "filmus"
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "7.1",
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(modifier = Modifier
                            .background(
                                color = colorResource(R.color.app_background),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .weight(1f)
                        ) {
                            Row {
                                Image(painter = painterResource(R.drawable.imdb_logo),
                                    contentDescription = "filmus"
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "7.3",
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
                    .padding(16.dp) // Добавляем padding, чтобы текст не прилипал к краям
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
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                                    text = "Германия, США",
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
                                    text = "16+",
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                                    text = "1 ч 30 мин",
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
                                    text = "2022",
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
                    .padding(16.dp) // Добавляем padding, чтобы текст не прилипал к краям
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
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier = Modifier
                                .background(
                                    color = colorResource(R.color.app_background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .weight(1.5f)
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Image(
                                    painter = painterResource(R.drawable.avatar_image),
                                    contentDescription = "avatar_1",
                                    Modifier.size(width = 48.dp, height = 48.dp)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "Баран бо Одар",
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
                        ) {
                            Text(
                                text = "триллер",
                                fontSize = 16.sp,
                                color = Color.White
                            )

                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = colorResource(R.color.app_background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {

                            Text(
                                text = "драма",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = colorResource(R.color.app_background),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {

                            Text(
                                text = "фантастика",
                                fontSize = 16.sp,
                                color = Color.White
                            )

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
                    .padding(16.dp) // Добавляем padding, чтобы текст не прилипал к краям
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
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                                    text = "$ 15 000 000",
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
                                    text = "$ 30 000 000",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    lineHeight = 20.sp
                                )
                            }
                        }

                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(height = 148.dp, width = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* Действие на нажатие */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "1899",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { /* Действие на нажатие */ }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.White
                )
            }
        }
    }
}




