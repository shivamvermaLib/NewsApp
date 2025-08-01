package com.app.newsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.app.newsapp.domain.model.Article
import com.app.newsapp.ui.viewmodel.NewsListUIState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    uiState: NewsListUIState,
    onItemClick: (Article) -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News List") },
            )
        }
    ) {
        val state = rememberPullToRefreshState()
        PullToRefreshBox(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            state = state,
            isRefreshing = uiState.isLoading,
            onRefresh = onRefresh
        ) {
            if (uiState.error != null) {
                Text(
                    "Error: ${uiState.error}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    textAlign = TextAlign.Center
                )
            } else if (uiState.newsList.isEmpty()) {
                Text(
                    "No news available", modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    textAlign = TextAlign.Center
                )
            } else {
                NewsList(
                    uiState.newsList,
                    onItemClick
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsList(
    list: List<Article>,
    onItemClick: (Article) -> Unit
) {
    LazyColumn {
        items(list) {
            NewsItem(it) { onItemClick(it) }
        }
    }
}

@Composable
fun NewsItem(item: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Published at: ${item.getPublishedAtFormatted()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            AsyncImage(
                model = item.urlToImage,
                contentDescription = item.title,
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
        }
    }
}
