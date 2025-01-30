package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinoteka.R
import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.presentation.model.FriendContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.model.MovieDetailsContent
import com.example.kinoteka.presentation.model.MovieRatingContent
import com.example.kinoteka.presentation.model.UserContent
import com.example.kinoteka.presentation.ui.screencompose.utils.calculateColor
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel

@Composable
fun ContentColumn(
    viewModel: MovieDetailsViewModel,
    details: MovieDetailsContent,
    movieRating: MovieRatingContent?,
    authorAvatar: Author,
    listState: LazyListState,
    user: UserContent?,
    friends: List<FriendContent>,
    genres: List<GenreContent>,
    onAddFriendClick: (FriendContent) -> Unit,
    onEditReviewClick: (String, Int) -> Unit,
    onAddGenreClick: (GenreContent) -> Unit,
    onDeleteGenreClick: (GenreContent) -> Unit,
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
            Spacer(Modifier.height(328.dp))
            TitleBox(details.name, details.tagline)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            if(!friends.isEmpty()) {
                FriendsLikeBox(friends)
                Spacer(modifier = Modifier.height(16.dp))
            }
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
                            text = stringResource(R.string.director),
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
                                    lineHeight = 20.sp,
                                    fontFamily = FontFamily(
                                        Font(R.font.manrope_medium, FontWeight.Thin)
                                    )
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
                            text = stringResource(R.string.genres),
                            color = colorResource(R.color.gray),
                            fontSize = 16.sp,
                            lineHeight = 20.sp
                        )

                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(details.genres) { genreItem ->
                            val isHighlighted = genres.any { it.genreId == genreItem.id }

                            Box(
                                modifier = Modifier
                                    .background(
                                        brush = if (isHighlighted) {
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    Color(0xFFDF2800),
                                                    Color(0xFFFF6633)
                                                ),
                                                start = Offset.Zero,
                                                end = Offset.Infinite
                                            )
                                        } else {
                                            SolidColor(colorResource(R.color.app_background))
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .clickable {
                                        if (isHighlighted) {
                                            onDeleteGenreClick(
                                                GenreContent(
                                                    genreId = genreItem.id, userId = user?.userId
                                                        ?: "", name = genreItem.name
                                                )
                                            )
                                        } else {
                                            onAddGenreClick(
                                                GenreContent(
                                                    genreId = genreItem.id, userId = user?.userId
                                                        ?: "", name = genreItem.name
                                                )
                                            )
                                        }
                                    }
                            ) {
                                Text(
                                    text = genreItem.name,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily(
                                        Font(R.font.manrope_medium, FontWeight.Thin)
                                    )
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
                            text = stringResource(R.string.money),
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
                                    text = stringResource(R.string.budget),
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
                                    text = stringResource(R.string.money_in_world),
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
                                text = stringResource(R.string.reviews),
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
                                            Modifier
                                                .size(width = 32.dp, height = 32.dp)
                                                .clickable {
                                                    onAddFriendClick(
                                                        FriendContent(
                                                            friendId = currentReview.author?.userId
                                                                ?: "Unknown",
                                                            userId = user?.userId ?: "",
                                                            nickName = currentReview.author?.nickName
                                                                ?: "Unknown",
                                                            avatar = currentReview.author?.avatar
                                                                ?: ""
                                                        )
                                                    )
                                                }
                                        )
                                    }
                                    else {
                                        AsyncImage(
                                            model = currentReview.author?.avatar,
                                            contentDescription = "Avatar",
                                            Modifier
                                                .size(width = 32.dp, height = 32.dp)
                                                .clip(CircleShape)
                                                .clickable(
                                                    enabled = !currentReview.isAnonymous,
                                                ) {
                                                    onAddFriendClick(
                                                        FriendContent(
                                                            friendId = currentReview.author?.userId
                                                                ?: "Unknown",
                                                            userId = user?.userId ?: "",
                                                            nickName = currentReview.author?.nickName
                                                                ?: "Unknown",
                                                            avatar = currentReview.author?.avatar
                                                                ?: ""
                                                        )
                                                    )
                                                },
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
                                                stringResource(R.string.anonimus)
                                            } else {
                                                currentReview?.author?.nickName ?: stringResource(R.string.anonimus)
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
                                        ),
                                    fontFamily = FontFamily(
                                        Font(R.font.manrope_regular, FontWeight.Bold)
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
                                    if (details.reviews[currentReviewIndex.value].author?.nickName == user?.nickName) {
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
                                    text = if (details.reviews[currentReviewIndex.value].author?.nickName == user?.nickName) {
                                        stringResource(R.string.edit_review)
                                    } else {
                                        stringResource(R.string.add_review)
                                    },
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }
                            if (details.reviews[currentReviewIndex.value].author?.nickName == user?.nickName){
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
                }

            }
        }
    }
}