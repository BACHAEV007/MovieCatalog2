package com.example.kinoteka.presentation.ui.screencompose.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinoteka.R
import com.example.kinoteka.presentation.model.GenreContent
import java.util.Locale

@Composable
fun GenreBlock(genre: GenreContent, onDeleteGenreClick: (GenreContent) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.not_selected_button),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = genre.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale("ru")) else it.toString()
            },
            fontSize = 16.sp,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(vertical = 18.dp),
            fontFamily = FontFamily(
                Font(R.font.manrope_medium, FontWeight.Normal)
            )
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(
                    color = colorResource(R.color.app_background),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.delete_genre_ic),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onDeleteGenreClick(genre)
                }
            )
        }

    }
}