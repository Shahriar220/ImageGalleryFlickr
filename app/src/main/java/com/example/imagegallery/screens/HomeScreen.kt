package com.example.imagegallery.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    imageViewModel: ImageViewModel = hiltViewModel(),
) {
    val screenState by imageViewModel.screenState.collectAsState()
    val searchQuery by imageViewModel.searchText.collectAsState()

    val onSearchQueryChanged: (String) -> Unit = { query ->
        imageViewModel.filterItems(query)
    }

    Scaffold(
        modifier = Modifier.padding(10.dp),
        topBar = {
            SearchBar(searchQuery = searchQuery, onSearchQueryChanged = onSearchQueryChanged)
        }
    ) {
        when (screenState) {
            is FlickrResponseState.Loading -> {
                LoaderScreen()
            }

            is FlickrResponseState.Error -> {
                DisplayAlertDialog(
                    onDismissRequest = { /* Handle Dismiss */ },
                    dialogTitle = "Error",
                    dialogText = (screenState as FlickrResponseState.Error).errorMessage,
                    icon = Icons.Default.Info
                )
            }

            is FlickrResponseState.Success -> {
                val response = (screenState as FlickrResponseState.Success).data

                if (response != null) {
                    LazyColumn(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)) {
                        items(response) { item ->
                            ShowImageCards(imageItem = item, onItemClicked = { cardItem ->
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "item",
                                    value = cardItem,
                                )
                                navController.navigate(Routes.DETAILS_ROUTE)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            label = { Text(text = "Enter Tag") },
        )
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