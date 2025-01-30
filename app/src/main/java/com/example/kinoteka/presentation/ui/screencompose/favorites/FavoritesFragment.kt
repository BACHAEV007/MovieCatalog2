import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kinoteka.R
import com.example.kinoteka.presentation.ui.screenview.FavoritesScreenFragment
import com.example.kinoteka.presentation.model.FavouriteContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.ui.screencompose.favorites.BlankScreen
import com.example.kinoteka.presentation.ui.screencompose.favorites.FavoriteMovies
import com.example.kinoteka.presentation.ui.screencompose.favorites.FavoritesGenres
import com.example.kinoteka.presentation.ui.screencompose.utils.GradientBackgroundHalfOverlay
import com.example.kinoteka.presentation.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, fragment: FavoritesScreenFragment, onMovieClick: (String) -> Unit){
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
                    Font(R.font.manrope_bold, FontWeight.Normal)
                )
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
        item {
            Spacer(modifier = Modifier.size(70.dp))
        }
    }
    if (genres.isEmpty() && favorites.isEmpty()){
        BlankScreen()
    }

}




