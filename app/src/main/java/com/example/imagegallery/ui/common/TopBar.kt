package com.example.imagegallery.ui.common

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imagegallery.domain.model.SearchEntity
import com.example.imagegallery.ui.theme.ImageGalleryTheme

/**
 * @author Shahriar
 * @since ১৯/৫/২৪ ৭:১৭ PM
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    leadingIcon: ImageVector? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    onLeadingIconClick: () -> Unit = {},
    onQueryChanged: ((String) -> Unit)? = null,
    showBottomSheetIcon: Boolean = true,
    doneButtonPressed: (String) -> Unit = {},
    deleteButtonPressed: (Int) -> Unit = {},
    onSelectedItemClicked: (String) -> Unit,
    searchList: List<SearchEntity>?
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

    val doneButtonClicked: (String) -> Unit = { query ->
        isSheetOpen = false
        doneButtonPressed.invoke(query)
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
        ) {
            ImageFilter(
                doneButtonClicked,
                deleteButtonPressed,
                onSelectedItemClicked,
                searchList = searchList
            )
        }
    }

    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        leadingIcon?.let {
            IconButton(onClick = onLeadingIconClick) {
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
            if (showBottomSheetIcon) {
                IconButton(
                    onClick = { isSheetOpen = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.alpha(if (isSearchBarExpanded) 0f else 1f)
                    )
                }
            }
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
                    onQueryChanged = {},
                    doneButtonPressed = {},
                    deleteButtonPressed = {},
                    onSelectedItemClicked = {},
                    searchList = null
                )
                TopBar(
                    title = "Details",
                    leadingIcon = Icons.Default.ArrowBack,
                    onQueryChanged = {},
                    doneButtonPressed = {},
                    deleteButtonPressed = {},
                    onSelectedItemClicked = {},
                    searchList = null
                )
            }
        }
    }
}