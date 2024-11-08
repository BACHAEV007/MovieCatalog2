package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
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
import coil.compose.rememberImagePainter
import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.Review
import com.example.kinoteka.presentation.model.FriendContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.model.MovieDetailsContent
import com.example.kinoteka.presentation.model.MovieRatingContent
import com.example.kinoteka.presentation.model.UserContent
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.example.kinoteka.presentation.ui.screencompose.utils.LoadAnimation

@Composable
fun MoviesDetailsScreen(viewModel: MovieDetailsViewModel, activity: Activity) {
    val movieDetails by viewModel.movieDetails
    val isFavourite by viewModel.isFavorite
    val movieRating by viewModel.movieRating
    val authorAvatar by viewModel.authorContent
    val user by viewModel.userContent
    val isLoading by viewModel.isLoading
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
    if (isLoading) {
        LoadAnimation()
    }
    else{
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
                        onAddGenreClick = { genre ->
                            viewModel.addGenre(genre)
                        },
                        onDeleteGenreClick = { genre ->
                            viewModel.deleteGenre(genre)
                        },
                        currentReviewIndex = currentReviewIndex
                    )
                }
            }
        }
    }

}

