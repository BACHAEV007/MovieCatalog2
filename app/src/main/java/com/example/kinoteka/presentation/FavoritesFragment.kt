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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kinoteka.R
import com.example.kinoteka.presentation.FavoritesScreenFragment
import com.example.kinoteka.presentation.model.FavouriteContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.model.MovieContent
import com.example.kinoteka.presentation.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, fragment: FavoritesScreenFragment){
    val genres by viewModel.genresContent.collectAsState(initial = emptyList())
    val favorites by viewModel.favouritesContent.collectAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.app_background))
            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = stringResource(R.string.Favorites),
            fontSize = 24.sp,
            color = colorResource(R.color.white)
        )
        if (!genres.isEmpty()){
            FavoritesGenres(
                genres,
                onDeleteGenreClick = { genre ->
                    viewModel.deleteGenre(genre)
                },
            )
        }
        if (!favorites.isEmpty()) {
            FavoriteMovies(favorites)
        }
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
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(genres){lang -> GenreBlock(lang, onDeleteGenreClick)}
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
            text = genre.name,
            fontSize = 16.sp,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(vertical = 18.dp)
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
fun FavoriteMovies(movies: List<FavouriteContent>) {
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
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
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
            }
        }
    }
}