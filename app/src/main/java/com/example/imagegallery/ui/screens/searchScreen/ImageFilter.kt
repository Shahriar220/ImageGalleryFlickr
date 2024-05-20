package com.example.imagegallery.ui.screens.searchScreen

/**
 * @author Shahriar
 * @since ২০/৫/২৪ ৮:০০ AM
 */

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.imagegallery.ui.theme.ImageGalleryTheme
import com.example.imagegallery.utils.ComposePreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageFilter(
    title: String="Image Gallery",
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    onLeadingIconClick: () -> Unit = {},
    onQueryChanged: ((String) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var keyword by remember { mutableStateOf("") }
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Type your tag")
            }
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ChipItem(tag = "Bangladesh")
            ChipItem(tag = "Nature")
            ChipItem(tag = "Sundarban")
            ChipItem(tag = "Mangrove Forest")
            ChipItem(tag = "Royal Bengal Tiger")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "All Tags")
            RadioButton(selected = true, onClick = {})
            Text(text = "Any Tag")
            RadioButton(selected = false, onClick = {})
        }
    }
}

@Composable
fun ChipItem(tag: String) {
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
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
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