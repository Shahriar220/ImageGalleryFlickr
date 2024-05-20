package com.example.imagegallery.ui.screens.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imagegallery.ui.screens.searchScreen.ImageFilter
import com.example.imagegallery.ui.theme.ImageGalleryTheme

/**
 * @author Shahriar
 * @since ১৯/৫/২৪ ৭:১৭ PM
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = Icons.Default.List,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    onLeadingIconClick: () -> Unit = {},
    doneButtonPressed: ((String) -> Unit) = {},
    onQueryChanged: ((String) -> Unit)? = null
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchBarExpanded by remember { mutableStateOf(false) }


    val resetSearch: () -> Unit = {
        searchQuery = ""
        onQueryChanged?.invoke("")
    }
    val focusRequester = remember { FocusRequester() }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val doneButtonPressed: (String) -> Unit = { query ->
        isSheetOpen = false
        onQueryChanged?.invoke(query)
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
        ) {
            ImageFilter(
                "Image Gallery",
                modifier = Modifier,
                doneButtonPressed,
                onQueryChanged,
            )
        }
    }

    Row(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        leadingIcon?.let {
            IconButton(onClick = { isSheetOpen = true }) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor
                )
            }
        } ?: Spacer(Modifier)

        if (isSearchBarExpanded) {
            IconButton(
                onClick = {
                    isSearchBarExpanded = false
                    resetSearch()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = contentColor
                )
            }
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    onQueryChanged?.invoke(query)
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            searchQuery = ""
                            onQueryChanged?.invoke("")
                        },
                        tint = contentColor
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = contentColor,
                    unfocusedIndicatorColor = contentColor,
                    cursorColor = contentColor
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .focusRequester(focusRequester)
            )
        } else {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = contentColor,
                modifier = Modifier.weight(1f)
            )
        }

        onQueryChanged?.let {
            IconButton(onClick = { isSearchBarExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = contentColor
                )
            }
        }

        LaunchedEffect(key1 = isSearchBarExpanded) {
            if (isSearchBarExpanded) {
                focusRequester.requestFocus()
            }
        }
    }
}


@Preview
@Composable
private fun TopBarPreview() {
    ImageGalleryTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TopBar(
                    title = "Flickr Gallery",
                    leadingIcon = Icons.Default.ArrowBack,
                    onQueryChanged = {}
                )
                TopBar(title = "Details")
            }
        }
    }
}