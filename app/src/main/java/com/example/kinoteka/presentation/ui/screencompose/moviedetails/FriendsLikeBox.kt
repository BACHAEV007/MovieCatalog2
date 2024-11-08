package com.example.kinoteka.presentation.ui.screencompose.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.kinoteka.R
import com.example.kinoteka.presentation.model.FriendContent

@Composable
fun FriendsLikeBox(friends: List<FriendContent>,) {
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
            LazyRow {
                items(friends.take(3)) { friend ->
                    Image(
                        painter = if (friend.avatar.isNullOrEmpty() || friend.avatar == "") {
                            painterResource(R.drawable.avatar_image)
                        } else {
                            rememberImagePainter(friend.avatar)
                        },
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "нравится ${friends.size} вашим друзьям",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(R.font.manrope_medium, FontWeight.Normal)
                )
            )
        }
    }
}