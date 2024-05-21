package com.example.imagegallery.ui.gallery

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
import com.example.imagegallery.constants.Routes
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.domain.model.SearchEntity
import com.example.imagegallery.ui.common.DisplayAlertDialog
import com.example.imagegallery.ui.common.LoaderScreen
import com.example.imagegallery.ui.common.TopBar
import com.example.imagegallery.ui.theme.ImageGalleryTheme
import com.example.imagegallery.utils.ComposePreview

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১০:০২ AM
 */
@Composable
fun GalleryScreen(
    navController: NavController,
    galleryViewModel: GalleryViewModel = hiltViewModel(),
) {
    val screenState by galleryViewModel.screenState.collectAsState()
    val searchList by galleryViewModel.searchList.collectAsState()

    val onSearchQueryChanged: (String) -> Unit = { query ->
        galleryViewModel.filterItems(query)
    }

    val doneButtonPressed: (String) -> Unit = { query ->
        galleryViewModel.addToDb(query);
        galleryViewModel.getImageData(query)
    }

    val deleteButtonPressed: (Int) -> Unit = { id ->
        galleryViewModel.deleteQuery(id)
    }

    val onSelectedItemClicked: (String) -> Unit = { query ->
        galleryViewModel.getImageData(query)
    }

    val onImageClicked: (Item) -> Unit = { item ->
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = "item",
            value = item
        )
        navController.navigate(Routes.DETAILS_ROUTE)
    }
    GalleryScreen(
        screenState = screenState,
        onSearchQueryChanged = onSearchQueryChanged,
        doneButtonPressed = doneButtonPressed,
        deleteButtonPressed = deleteButtonPressed,
        onSelectedItemClicked = onSelectedItemClicked,
        onImageClicked = onImageClicked,
        searchListEntities = searchList
    )
}

@Composable
fun GalleryScreen(
    screenState: FlickrResponseState,
    onSearchQueryChanged: (String) -> Unit = {},
    doneButtonPressed: (String) -> Unit = {},
    deleteButtonPressed: (Int) -> Unit = {},
    onSelectedItemClicked: (String) -> Unit = {},
    onImageClicked: (Item) -> Unit = {},
    searchListEntities: List<SearchEntity>
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Flickr Gallery",
                onQueryChanged = onSearchQueryChanged,
                doneButtonPressed = doneButtonPressed,
                deleteButtonPressed = deleteButtonPressed,
                onSelectedItemClicked = onSelectedItemClicked,
                searchList = searchListEntities
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
        GalleryScreen(
            screenState = FlickrResponseState.Success(
                data = emptyList()
            ),
            onSearchQueryChanged = {},
            doneButtonPressed = { },
            deleteButtonPressed = { },
            onSelectedItemClicked = { },
            onImageClicked = { },
            searchListEntities = emptyList()
        )
    }
}