package com.example.kinoteka.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.MovieRating
import com.example.kinoteka.domain.model.ReviewModify
import com.example.kinoteka.domain.model.UserRegisterModel
import com.example.kinoteka.domain.usecase.AddFriendUseCase
import com.example.kinoteka.domain.usecase.AddGenreUseCase
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.AddReviewUseCase
import com.example.kinoteka.domain.usecase.DeleteGenreUseCase
import com.example.kinoteka.domain.usecase.DeleteMovieFromFavouritesUseCase
import com.example.kinoteka.domain.usecase.DeleteReviewUseCase
import com.example.kinoteka.domain.usecase.EditReviewUseCase
import com.example.kinoteka.domain.usecase.FetchFriendsUseCase
import com.example.kinoteka.domain.usecase.FetchGenresUseCase
import com.example.kinoteka.domain.usecase.GetAuthorInfoUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMovieDetailsUseCase
import com.example.kinoteka.domain.usecase.GetMovieRatingUseCase
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.presentation.mapper.EntityMapper
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.model.FriendContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.model.MovieDetailsContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
    private val deleteMovieFromFavouritesUseCase: DeleteMovieFromFavouritesUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val getMovieRatingUseCase: GetMovieRatingUseCase,
    private val getMoviesDetailsUseCase: GetMovieDetailsUseCase,
    private val getAuthorInfoUseCase: GetAuthorInfoUseCase,
    private val addReviewUseCase : AddReviewUseCase,
    private val editReviewUseCase : EditReviewUseCase,
    private val deleteReviewUseCase : DeleteReviewUseCase,
    private val getProfileInfoUseCase : GetProfileInfoUseCase,
    private val contentMapper: MoviesMapper,
    private val entityMapper: EntityMapper,
    private val addFriendUseCase: AddFriendUseCase,
    private val fetchFriendsUseCase: FetchFriendsUseCase,
    private val addGenreUseCase: AddGenreUseCase,
    private val deleteGenreUseCase: DeleteGenreUseCase,
    private val fetchGenresUseCase: FetchGenresUseCase
) : ViewModel() {
    private val _movieDetails: MutableState<MovieDetailsContent?> = mutableStateOf(null)
    val movieDetails: State<MovieDetailsContent?>
        get() = _movieDetails

    val friendsContent: Flow<List<FriendContent>> = fetchFriendsUseCase()
        .map {  friendModelList ->
            friendModelList.map { entityMapper.mapToContent(it) }
        }

    val genresContent: Flow<List<GenreContent>> = fetchGenresUseCase()
        .map { genreDbList ->
            genreDbList.map { entityMapper.mapToContent(it) }
        }

    var isDialogShown by mutableStateOf(false)

    fun onAcceptClick(){
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
    }

    fun addFriend(friend: FriendContent) {
        viewModelScope.launch {
            addFriendUseCase(entityMapper.mapToDbModel(friend))
        }
    }

    private val _movieRating: MutableState<MovieRating?> = mutableStateOf(null)
    val movieRating: State<MovieRating?>
        get() = _movieRating

    private val _userContent: MutableState<String?> = mutableStateOf(null)
    val userContent: State<String?>
        get() = _userContent

    private val _isFavorite: MutableState<Boolean> = mutableStateOf(false)
    val isFavorite: State<Boolean>
        get() = _isFavorite

    private val _authorContent: MutableState<Author?> = mutableStateOf(null)
    val authorContent: State<Author?>
        get() = _authorContent

    private val _reviewContent: MutableState<ReviewModify?> = mutableStateOf(null)
    val reviewContent: State<ReviewModify?>
        get() = _reviewContent


    fun submitReview(movieId: String, reviewText: String, rating: Int, anonymous: Boolean) {
        val reviewModify = ReviewModify(
            reviewText = reviewText,
            rating = rating,
            isAnonymous = anonymous
        )
        viewModelScope.launch {
            addReviewUseCase(movieId, reviewModify)
        }
    }

    fun updateReview(movieId: String, id: String, reviewText: String, rating: Int, anonymous: Boolean) {
        val reviewModify = ReviewModify(
            reviewText = reviewText,
            rating = rating,
            isAnonymous = anonymous
        )
        viewModelScope.launch {
            editReviewUseCase(movieId, id, reviewModify)
        }
    }

    fun deleteReview(movieId: String, id: String) {
        viewModelScope.launch {
            deleteReviewUseCase(movieId, id)
        }
    }

    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                val favouriteMovies = getFavouritesUseCase()
                val movieDetailsModel = getMoviesDetailsUseCase(movieId)
                val director = movieDetailsModel.director.substringBefore(",")
                val author = getAuthorInfoUseCase(director)
                val movieRating = getMovieRatingUseCase(movieDetailsModel.name, movieDetailsModel.year)
                val user = getProfileInfoUseCase().nickName
                _isFavorite.value = favouriteMovies.any { it.id == movieDetailsModel.id }
                _movieDetails.value = contentMapper.mapToMovieDetailsContent(movieDetailsModel)
                _movieRating.value = movieRating
                _authorContent.value = author
                _userContent.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToFavorites(movieId: String) {
        viewModelScope.launch {
            try {
                addMovieToFavoritesUseCase(movieId)
                _isFavorite.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFromFavorites(movieId: String) {
        viewModelScope.launch {
            try {
                deleteMovieFromFavouritesUseCase(movieId)
                _isFavorite.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}