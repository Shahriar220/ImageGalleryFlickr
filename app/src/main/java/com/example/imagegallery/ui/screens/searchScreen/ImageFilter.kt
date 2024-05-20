package com.example.imagegallery.ui.screens.searchScreen

/**
 * @author Shahriar
 * @since ২০/৫/২৪ ৮:০০ AM
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imagegallery.ui.theme.ImageGalleryTheme
import com.example.imagegallery.utils.ComposePreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageFilter(
    title: String = "Image Gallery",
    modifier: Modifier = Modifier,
    doneButtonPressed: (String) -> Unit = {},
    onQueryChanged: ((String) -> Unit)? = null,
    imageFilterViewModel: ImageFilterViewModel = hiltViewModel()
) {
    val searchList by imageFilterViewModel.searchList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val onDeleteIconClicked: (Int) -> (Unit) = { id ->
        imageFilterViewModel.deleteQuery(id)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Type your tag")
            }, trailingIcon = {
                IconButton(onClick = {
                    imageFilterViewModel.addToDb(searchQuery)
                    doneButtonPressed.invoke(searchQuery)
                }) {
                    Icon(Icons.Default.Done, contentDescription = "Done")
                }
            }
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            searchList.take(4).forEach { searchEntity ->
                ChipItem(tag = searchEntity.query, id = searchEntity.id, onDeleteIconClicked)
            }
        }

//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(text = "All Tags")
//            RadioButton(selected = true, onClick = {})
//            Text(text = "Any Tag")
//            RadioButton(selected = false, onClick = {})
//        }
    }
}

@Composable
fun ChipItem(tag: String, id: Int, onDeleteClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .background(Color.LightGray, CircleShape)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tag,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.clickable { onDeleteClicked(id) }
            )
        }
    }
}

@ComposePreview
@Composable
private fun ImageFilterPreview() {
    ImageGalleryTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ImageFilter()
        }
    }
}