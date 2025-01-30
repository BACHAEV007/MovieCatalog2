package com.example.kinoteka.presentation.ui.screencompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.kinoteka.R
import com.example.kinoteka.domain.model.Review

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