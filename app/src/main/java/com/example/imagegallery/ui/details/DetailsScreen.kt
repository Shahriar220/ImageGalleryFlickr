package com.example.imagegallery.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import coil.compose.AsyncImage
import com.example.imagegallery.R
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.data.response.Media
import com.example.imagegallery.ui.common.TopBar
import com.example.imagegallery.ui.theme.ImageGalleryTheme
import com.example.imagegallery.utils.ComposePreview
import com.example.imagegallery.utils.dateTimeConverterUtil

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ৭:৪৮ PM
 */
@Composable
fun DetailsScreen(
    item: Item,
    onBackPress: () -> Unit = {},
) {
    val context = LocalContext.current
    val onDownloadClick = {

    }
    val onEmailClick = {
        val recipient = "shahriar.uchchash@gmail.com"

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$recipient")
            putExtra("Image_URL", item.media?.m)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    val onShareClick = {
        val url = item.media?.m

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }


    Scaffold(
        topBar = {
            TopBar(
                title = "Image Details",
                leadingIcon = Icons.Default.ArrowBack,
                onLeadingIconClick = onBackPress,
                showBottomSheetIcon = false,
                onSelectedItemClicked = {},
                searchList = emptyList()
            )
        }
    ) { paddingValues ->
        DetailsData(
            modifier = Modifier.padding(paddingValues),
            item = item,
            onEmailClick = onEmailClick,
            onShareClick = onShareClick,
            onDownloadClick = onDownloadClick
        )
    }
}

@Composable
fun DetailsData(
    item: Item,
    modifier: Modifier = Modifier,
    onEmailClick: () -> Unit,
    onShareClick: () -> Unit,
    onDownloadClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = item.media?.m,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.title ?: "", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                IconRow(
                    onShareClick = onShareClick,
                    onDownloadClick = onDownloadClick,
                    onEmailClick = onEmailClick
                )
            }

            CommonTextView(
                text = ("Author: " + item.author),
                style = MaterialTheme.typography.titleMedium
            )
            CommonTextView(
                text = ("Published At: " + item.published?.let { dateTimeConverterUtil(it) }),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            val descriptionText = item.description?.parseAsHtml(0)
            Text(text = descriptionText.toString())
        }
    }
}

@Composable
fun CommonTextView(text: String, style: TextStyle) {
    Text(text = text, style = style)
}

@Composable
fun IconRow(
    onShareClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onEmailClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Share",
            modifier = Modifier
                .size(24.dp)
                .clickable { onShareClick() },
            tint = MaterialTheme.colorScheme.primary
        )
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Download",
            modifier = Modifier
                .size(24.dp)
                .clickable { onDownloadClick() },
            tint = MaterialTheme.colorScheme.primary
        )
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Email",
            modifier = Modifier
                .size(24.dp)
                .clickable { onEmailClick() },
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@ComposePreview
@Composable
private fun DetailsScreenPreview() {
    ImageGalleryTheme {
        DetailsScreen(
            item = Item(
                author = "Author",
                authorId = "author_id",
                dateTaken = "2024-02-08T17:36:32-08:00",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                link = "https:\\/\\/www.flickr.com\\/photos\\/197607119@N07\\/53729929107\\/",
                media = Media(m = "https:\\/\\/live.staticflickr.com\\/65535\\/53729929107_5115391541_m.jpg"),
                published = "2024-05-19T06:27:03Z",
                tags = "",
                title = "IMG_6424.jpg"
            )
        )
    }
}