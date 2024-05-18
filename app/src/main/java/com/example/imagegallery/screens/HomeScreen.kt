package com.example.imagegallery.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val openAlertDialog = remember { mutableStateOf(false) }

    val onItemClicked = { cardItem: Item ->
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = "item",
            value = cardItem,
        )
        navController.navigate(Routes.DETAILS_ROUTE)
    }

    Surface(modifier = Modifier.fillMaxWidth()) {
        when (screenState) {
            is FlickrResponseState.Loading -> {
                LoaderScreen()
            }

            is FlickrResponseState.Error -> {
                if (!openAlertDialog.value)
                    DisplayAlertDialog(
                        onDismissRequest = { openAlertDialog.value = true },
                        dialogTitle = "Error",
                        dialogText = "Something Went Wrong",
                        icon = Icons.Default.Info
                    )
            }

            is FlickrResponseState.Success -> {
                val response = (screenState as FlickrResponseState.Success).data

                val responseItem = response.items;
                if (responseItem != null) {
                    LazyColumn {
                        items(responseItem) { item ->
                            ShowImageCards(imageItem = item, onItemClicked)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowImageCards(
    imageItem: Item,
    onItemClicked: (Item) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClicked(imageItem) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = imageItem.media?.m, contentDescription = "",
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background)
            )
        }
    }
}