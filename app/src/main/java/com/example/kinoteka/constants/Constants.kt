package com.example.kinoteka.constants

object Constants {
    const val BASE_URL = "https://react-midterm.kreosoft.space/api/"
    const val REGISTER_URL = "account/register"
    const val LOGIN_URL = "account/login"
    const val LOGOUT_URL = "account/logout"
    const val TOKEN = "TOKEN"
    const val SHARED_PREFERENCES_NAME = "MY_APP"
    const val GET_MOVIES_URL = "movies/{page}"
    const val GET_MOVIE_DETAILS_URL = "movies/details/{id}"
    const val GET_FAVORITES_URL = "favorites"
    const val ADD_FAVORITE_URL = "favorites/{id}/add"
    const val DELETE_FAVORITE_URL = "favorites/{id}/delete"
    const val ACCOUNT_PROFILE_URL = "account/profile"
    const val EDIT_REVIEW_URL = "movie/{movieId}/review/{id}/edit"
    const val ADD_REVIEW_URL = "movie/{movieId}/review/add"
    const val DELETE_REVIEW_URL = "movie/{movieId}/review/{id}/delete"
}