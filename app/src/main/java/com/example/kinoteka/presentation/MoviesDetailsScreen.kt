package com.example.kinoteka.presentation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.example.kinoteka.R
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.MovieRating
import com.example.kinoteka.domain.model.Review
import com.example.kinoteka.presentation.model.FriendContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.model.MovieDetailsContent

@Composable
fun MoviesDetailsScreen(viewModel: MovieDetailsViewModel, activity: Activity) {
    val movieDetails by viewModel.movieDetails
    val isFavourite by viewModel.isFavorite
    val movieRating by viewModel.movieRating
    val authorAvatar by viewModel.authorContent
    val user by viewModel.userContent
    var isTitleHidden by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var currentReviewIndex = remember { mutableStateOf(0) }

    isTitleHidden = listState.firstVisibleItemIndex > 0

    var inputText by remember { mutableStateOf("") }
    var userMark by remember { mutableStateOf(0) }
    var showDialog = viewModel.isDialogShown
    var isEditMode by remember { mutableStateOf(false) }

    val friends by viewModel.friendsContent.collectAsState(initial = emptyList())
    val genres by viewModel.genresContent.collectAsState(initial = emptyList())

    fun openDialogWithReviewText(reviewText: String, rating: Int) {
        inputText = reviewText
        userMark = rating
        viewModel.onAcceptClick()
    }

    if (showDialog) {
        CustomDialog(
            inputText = inputText,
            inputMark = userMark.toFloat(),
            onInputTextChange = { inputText = it },
            onDismiss = { viewModel.onDismissDialog() },
            onConfirm = { reviewText, rating, anonymous ->
                if (isEditMode) {
                    viewModel.updateReview(
                        movieId = movieDetails?.id.toString(),
                        id = movieDetails!!.reviews[currentReviewIndex.value].id,
                        reviewText = reviewText,
                        rating = rating.toInt(),
                        anonymous = anonymous
                    )
                } else {
                    viewModel.submitReview(
                        movieId = movieDetails?.id.toString(),
                        reviewText = reviewText,
                        rating = rating.toInt(),
                        anonymous = anonymous
                    )
                }
                viewModel.onDismissDialog()
            }
        )
    }

    movieDetails?.let { details ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.app_background))
        ) {
            PosterImage(details.poster, isTitleHidden)
            HeaderRow(activity, details.name, isFavourite, isTitleHidden) {
                if (isFavourite) {
                    viewModel.removeFromFavorites(details.id)
                } else {
                    viewModel.addToFavorites(details.id)
                }
            }
            authorAvatar?.let {
                ContentColumn(
                    viewModel,
                    details,
                    movieRating,
                    it,
                    listState,
                    user,
                    friends,
                    genres,
                    onAddFriendClick = { friend ->
                        viewModel.addFriend(friend)
                    },
                    onEditReviewClick = { reviewText, rating ->
                    openDialogWithReviewText(reviewText,rating)
                        isEditMode = true
                    },
                    currentReviewIndex = currentReviewIndex
                )
            }
        }
    }
}


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

@Composable
fun HeaderRow(activity: Activity, movieTitle: String, isFavourite: Boolean,
              isTitleHidden: Boolean, onFavoriteClick: () -> Unit) {
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
                .size(40.dp)
                .background(colorResource(R.color.not_selected_button))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_ic_full),
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Spacer(Modifier.size(16.dp))
        if (isTitleHidden) {
            Text(
                text = movieTitle,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(y = (-4).dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        IconButton(
            onClick = onFavoriteClick,
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(40.dp)
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
}

@Composable
fun ContentColumn(
    viewModel: MovieDetailsViewModel,
    details: MovieDetailsContent,
    movieRating: MovieRating?,
    authorAvatar: Author,
    listState: LazyListState,
    user: String?,
    friends: List<FriendContent>,
    genres: List<GenreContent>,
    onAddFriendClick: (FriendContent) -> Unit,
    onEditReviewClick: (String, Int) -> Unit,
    currentReviewIndex: MutableState<Int>
) {
    val reviews = details?.reviews ?: emptyList()

    val currentReview = reviews.getOrNull(currentReviewIndex.value)

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 96.dp, start = 24.dp, end = 24.dp)
    ) {
        item {
            Spacer(Modifier.height(359.dp))
            TitleBox(details.name, details.tagline)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            FriendsLikeBox()
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            details.description?.let { description ->
                if (description != "-") {
                    DescriptionBox(description)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        item {
            RatingBox(movieRating)
            Spacer(modifier = Modifier.size(16.dp))
        }
        item {
            InfoBox(details)
        }
        item{
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
        }
        item{
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
        }
        item{
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
        }
        item{
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
                                    if (currentReview?.author?.avatar == "" || currentReview.isAnonymous){
                                        Image(
                                            painter = painterResource(R.drawable.avatar_image),
                                            contentDescription = "avatar_1",
                                            Modifier.size(width = 32.dp, height = 32.dp)
                                        )
                                    }
                                    else {
                                        AsyncImage(
                                            model = currentReview.author?.avatar,
                                            contentDescription = "Avatar",
                                            Modifier
                                                .size(width = 32.dp, height = 32.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .padding(
                                                start = 8.dp,
                                                end = 8.dp
                                            )
                                            .fillMaxWidth(0.77f)
                                    ) {
                                        Text(
                                            text = if (currentReview?.isAnonymous == true) {
                                                "Анонимный пользователь"
                                            } else {
                                                currentReview?.author?.nickName ?: "Анонимный пользователь"
                                            },
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
                                    val backgroundColor = calculateColor(currentReview)
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(backgroundColor)
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
                                onClick = {
                                    if (details.reviews[currentReviewIndex.value].author?.nickName == user) {
                                        val existingReviewText = details.reviews[currentReviewIndex.value].reviewText
                                        onEditReviewClick(existingReviewText, details.reviews[currentReviewIndex.value].rating)
                                    } else {
                                        viewModel.onAcceptClick()
                                    }
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(end = 4.dp)
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
                                    text = if (details.reviews[currentReviewIndex.value].author?.nickName == user) {
                                        "Изменить отзыв"
                                    } else {
                                        "Добавить отзыв"
                                    },
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }
                            if (details.reviews[currentReviewIndex.value].author?.nickName == user){
                                IconButton(
                                    onClick = {
                                        viewModel.deleteReview(details.id, details.reviews[currentReviewIndex.value].id)
                                    },
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(colorResource(R.color.app_background))

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delete_ic),
                                        contentDescription = null,
                                        tint =  Color.White,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.size(20.dp))

                            IconButton(
                                onClick = {
                                    if (currentReviewIndex.value > 0) {
                                        currentReviewIndex.value -= 1
                                    }
                                },
                                enabled = currentReviewIndex.value > 0,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (currentReviewIndex.value > 0) colorResource(R.color.app_background) else colorResource(
                                            R.color.not_selected_button
                                        )
                                    )

                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.back_ic),
                                    contentDescription = null,
                                    tint = if (currentReviewIndex.value > 0) Color.White else Color.Gray,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (currentReviewIndex.value < reviews.size - 1) {
                                        currentReviewIndex.value += 1
                                    }
                                },
                                enabled = currentReviewIndex.value < reviews.size - 1,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (currentReviewIndex.value < reviews.size - 1) colorResource(
                                            R.color.app_background
                                        ) else colorResource(R.color.not_selected_button)
                                    )

                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.forward_ic),
                                    contentDescription = null,
                                    tint = if (currentReviewIndex.value < reviews.size - 1) Color.White else Color.Gray,
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

@Composable
fun calculateColor(currentReview: Review?): Color {
    return when {
        currentReview?.rating == null -> colorResource(R.color.marks_nine)
        currentReview.rating < 2 -> colorResource(R.color.marks_one)
        currentReview.rating < 3 -> colorResource(R.color.marks_two)
        currentReview.rating < 4 -> colorResource(R.color.marks_three)
        currentReview.rating < 5 -> colorResource(R.color.marks_four)
        currentReview.rating < 6 -> colorResource(R.color.marks_five)
        currentReview.rating < 7 -> colorResource(R.color.marks_six)
        currentReview.rating < 8 -> colorResource(R.color.marks_seven)
        currentReview.rating < 9 -> colorResource(R.color.marks_eight)
        else -> colorResource(R.color.marks_nine)
    }
}


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
            Text(text = name, color = Color.White, fontSize = 36.sp)
            tagline?.takeIf { it != "-" }?.let {
                Text(text = it, color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun FriendsLikeBox() {
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
            modifier = Modifier.fillMaxWidth(),
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
                fontSize = 16.sp
            )
        }
    }
}

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
            lineHeight = 20.sp
        )
    }
}

@Composable
fun RatingBox(movieRating: MovieRating?) {
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
}

@Composable
fun InfoBox(details: MovieDetailsContent) {
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
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    onInputTextChange: (String) -> Unit,
    onDismiss:()->Unit,
    onConfirm:(reviewText: String, rating: Float, anonymous: Boolean)->Unit,
    inputText: String = "",
    inputMark: Float = 5f
) {
    var body by remember { mutableStateOf(inputText) }
    var sliderValue by remember { mutableStateOf(inputMark) }
    var isFocusedBody by remember { mutableStateOf(false) }
    val bodyHint = "Текст отзыва"
    var checked by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.app_background)
            ),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .border(
                    1.dp,
                    color = colorResource(R.color.app_background),
                    shape = RoundedCornerShape(28.dp)
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Добавить отзыв",
                    fontSize = 20.sp,
                    color = colorResource(R.color.white),
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = "Оценка",
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Выбранная оценка: ${sliderValue.toInt()}",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Slider(
                    value = sliderValue,
                    onValueChange = { newValue ->
                        sliderValue = newValue
                    },
                    valueRange = 0f..10f,
                    steps = 9,
                    colors = SliderDefaults.colors(
                        thumbColor = colorResource(R.color.average_gradient_color),
                        activeTrackColor = colorResource(R.color.average_gradient_color),
                        inactiveTrackColor = colorResource(R.color.not_selected_button)
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(height = 120.dp, width = 0.dp)
                        .background(
                            color = colorResource(R.color.not_selected_button),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    BasicTextField(
                        value = if (body.isEmpty() && !isFocusedBody) bodyHint else body,
                        onValueChange = { newValue -> body = newValue },
                        textStyle = TextStyle(
                            color = if (body.isEmpty() && !isFocusedBody) Color.Gray else Color.White,
                            fontSize = 16.sp,
                        ),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                isFocusedBody = focusState.isFocused
                                if (body.isEmpty() && !isFocusedBody) body = ""
                            }
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                ){
                    Text(
                        text = "Анонимный отзыв",
                        fontSize = 14.sp,
                        color = colorResource(R.color.gray)
                    )
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(R.color.white),
                            checkedTrackColor = colorResource(R.color.average_gradient_color),
                            uncheckedThumbColor = colorResource(R.color.white),
                            uncheckedTrackColor = colorResource(R.color.gray_faded),
                        )
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(modifier = Modifier.weight(1.75f))
                    TextButton(
                        onClick = { onConfirm(body, sliderValue, checked) },
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
                            .weight(1.25f)
                    ) {
                        Text(
                            text = "Отправить",
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}








