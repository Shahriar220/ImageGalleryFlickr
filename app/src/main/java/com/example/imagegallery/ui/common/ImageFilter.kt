package com.example.imagegallery.ui.common

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.imagegallery.domain.model.SearchEntity
import com.example.imagegallery.ui.theme.ImageGalleryTheme
import com.example.imagegallery.utils.ComposePreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageFilter(
    doneButtonPressed: (String) -> Unit = {},
    deleteButtonPressed: (Int) -> Unit = {},
    onItemClicked: (String) -> Unit,
    searchList: List<SearchEntity>?
) {
    var searchQuery by remember { mutableStateOf("") }

    val onDeleteIconClicked: (Int) -> (Unit) = { id ->
        deleteButtonPressed.invoke(id)
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
                Text("Search By Tag")
            }, trailingIcon = {
                IconButton(onClick = {
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
            searchList?.take(4)?.forEach { searchEntity ->
                ChipItem(
                    tag = searchEntity.query,
                    id = searchEntity.id,
                    onDeleteClicked = onDeleteIconClicked,
                    onItemClicked
                )
            }
        }
    }
}

@Composable
fun ChipItem(
    tag: String,
    id: Int,
    onDeleteClicked: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .background(Color.LightGray, CircleShape)
            .clickable { onItemClicked(tag) }
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
            ImageFilter(
                doneButtonPressed = {},
                deleteButtonPressed = {},
                onItemClicked = {},
                searchList = null,
            )
        }
    }
}
