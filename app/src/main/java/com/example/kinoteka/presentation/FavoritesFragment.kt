import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kinoteka.R
import com.example.kinoteka.presentation.FavoritesScreenFragment
import com.example.kinoteka.presentation.GradientBackground
import com.example.kinoteka.presentation.model.FavouriteContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.model.MovieContent
import com.example.kinoteka.presentation.viewmodel.FavoritesViewModel
import java.util.regex.Pattern

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, fragment: FavoritesScreenFragment,onMovieClick: (String) -> Unit){
    val genres by viewModel.genresContent.collectAsState(initial = emptyList())
    val favorites by viewModel.favouritesContent.collectAsState(initial = emptyList())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.app_background))
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.Favorites),
                    fontSize = 24.sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(
                        Font(R.font.manrope_bold, FontWeight.Normal))
                )
            }
            if (genres.isNotEmpty()) {
                item {
                    FavoritesGenres(
                        genres = genres,
                        onDeleteGenreClick = { genre ->
                            viewModel.deleteGenre(genre)
                        }
                    )
                }
            }
            if (favorites.isNotEmpty()) {
                item {
                    FavoriteMovies(favorites, onMovieClick)
                }
            }
            item{
                Spacer(modifier = Modifier.size(70.dp))
            }
        }
    if (genres.isEmpty() && favorites.isEmpty()){
        BlankScreen()
    }

}

@Composable
fun FavoritesGenres(genres: List<GenreContent>, onDeleteGenreClick: (GenreContent) -> Unit,) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.favorite_genres),
            fontSize = 20.sp,
            style = TextStyle(
                brush =
                Brush.linearGradient(
                    colors = listOf(Color(0xFFDF2800), Color(0xFFFF6633)),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
            fontFamily = FontFamily(
                Font(R.font.manrope_bold, FontWeight.Normal))
        )
        Spacer(modifier = Modifier.size(16.dp))
        genres.forEach { genre ->
            GenreBlock(genre, onDeleteGenreClick)
            Spacer(modifier = Modifier.size(8.dp))
        }

    }
}

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
                Font(R.font.manrope_medium, FontWeight.Normal))
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

@Composable
fun FavoriteMovies(movies: List<FavouriteContent>,
                   onMovieClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            text = stringResource(R.string.favorites_movies),
            fontSize = 20.sp,
            style = TextStyle(
                brush =
                Brush.linearGradient(
                    colors = listOf(Color(0xFFDF2800), Color(0xFFFF6633)),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
            fontFamily = FontFamily(
                Font(R.font.manrope_bold, FontWeight.Normal))
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.heightIn(max = 1000.dp)
        ) {
            items(movies) { movie ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMovieClick(movie.id) }
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.7f),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        AsyncImage(
                            model = movie.poster,
                            contentDescription = "Movie Poster",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    val backgroundColor = when {
                        movie.rating.toFloat() < 2 -> colorResource(R.color.marks_one)
                        movie.rating.toFloat() < 3 -> colorResource(R.color.marks_two)
                        movie.rating.toFloat() < 4 -> colorResource(R.color.marks_three)
                        movie.rating.toFloat() < 5 -> colorResource(R.color.marks_four)
                        movie.rating.toFloat() < 6 -> colorResource(R.color.marks_five)
                        movie.rating.toFloat() < 7 -> colorResource(R.color.marks_six)
                        movie.rating.toFloat() < 8 -> colorResource(R.color.marks_seven)
                        movie.rating.toFloat() < 9 -> colorResource(R.color.marks_eight)
                        else -> colorResource(R.color.marks_nine)
                    }
                    Box(
                       modifier = Modifier.padding(8.dp)
                    ){
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .background(
                                    color = backgroundColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                text = movie.rating.toString(),
                                color = colorResource(R.color.white),
                                fontSize = 12.sp,
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun GradientBackgroundHalfOverlay(
    bottom: Boolean = true,
    modifier: Modifier = Modifier
) {
    val gradientColors = if (bottom) {
        listOf(Color(0x001A1A1A), Color(0xFF1A1A1A))
    } else {
        listOf(Color(0xFF1E1E1E), Color(0x001E1E1E))
    }

    val gradientBrush = Brush.verticalGradient(
        colors = gradientColors,
        startY = 100f,
        endY = 800f
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(gradientBrush)
    )
}


@Composable
fun BlankScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.welcome_screen_bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            GradientBackgroundHalfOverlay(
                bottom = false,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            GradientBackgroundHalfOverlay(
                bottom = true,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            Text(
                text = stringResource(R.string.Favorites),
                fontSize = 24.sp,
                color = colorResource(R.color.white),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 24.dp, start = 24.dp),
                fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Normal))
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 104.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.its_empty_now),
                color = colorResource(R.color.white),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Normal))
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.add_favorites_genres_and_movies),
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.manrope_medium, FontWeight.Normal)),
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.size(24.dp))
            TextButton(
                onClick = { },
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
            ) {
                Text(
                    text = stringResource(R.string.find_movie),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Normal)),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}