package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kinoteka.R

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