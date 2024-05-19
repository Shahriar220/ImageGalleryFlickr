package com.example.imagegallery.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.imagegallery.R
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.routes.Routes
import com.example.imagegallery.ui.theme.ImageGalleryTheme
import com.example.imagegallery.utils.ComposePreview
import com.example.imagegallery.viewmodels.FlickrResponseState
import com.example.imagegallery.viewmodels.ImageViewModel

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১০:০২ AM
 */
@Composable
fun HomeScreen(
    navController: NavController,
    imageViewModel: ImageViewModel = hiltViewModel(),
) {
    val screenState by imageViewModel.screenState.collectAsState()

    val onSearchQueryChanged: (String) -> Unit = { query ->
        imageViewModel.filterItems(query)
    }

    val onImageClicked: (Item) -> Unit = { item ->
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = "item",
            value = item
        )
        navController.navigate(Routes.DETAILS_ROUTE)
    }
    HomeScreen(
        screenState = screenState,
        onSearchQueryChanged = onSearchQueryChanged,
        onImageClicked = onImageClicked
    )
}

@Composable
fun HomeScreen(
    screenState: FlickrResponseState,
    onSearchQueryChanged: (String) -> Unit = {},
    onImageClicked: (Item) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Flickr Gallery",
                onQueryChanged = onSearchQueryChanged
            )
        }
    ) { paddingValues ->
        when (screenState) {
            is FlickrResponseState.Loading -> {
                LoaderScreen(modifier = Modifier.padding(paddingValues))
            }

            is FlickrResponseState.Error -> {
                DisplayAlertDialog(
                    onDismissRequest = { },
                    dialogTitle = "Error",
                    dialogText = screenState.errorMessage,
                    icon = Icons.Default.Info
                )
            }

            is FlickrResponseState.Success -> {
                val images = screenState.data

                if (images != null) {
                    Box(contentAlignment = Alignment.Center) {
                        if (images.isNotEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 200.dp),
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .fillMaxWidth()
                            ) {
                                items(
                                    items = images,
                                ) { image ->
                                    ImageCard(
                                        image = image,
                                        onItemClicked = onImageClicked
                                    )
                                }
                            }
                        } else {
                            Text(text = "No image found")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCard(
    image: Item,
    onItemClicked: (Item) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClicked(image) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = image.media?.m, contentDescription = "",
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background)
            )
        }
    }
}

@ComposePreview
@Composable
private fun HomeScreenPreview() {
    ImageGalleryTheme {
        HomeScreen(
            screenState = FlickrResponseState.Success(
                data = emptyList()
            )
        )
    }
}