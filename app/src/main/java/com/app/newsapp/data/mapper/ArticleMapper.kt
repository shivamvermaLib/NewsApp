package com.app.newsapp.data.mapper

import com.app.newsapp.data.NewsResponse
import com.app.newsapp.domain.model.Article

fun NewsResponse.Article.toDomainModel(): Article {
    return Article(
        id = url ?: "",
        author = author,
        title = title ?: "No Title",
        description = description,
        url = url ?: "",
        urlToImage = urlToImage,
        publishedAt = publishedAt ?: "",
        content = content,
        sourceName = source?.name
    )
}

fun List<NewsResponse.Article>.toDomainModel(): List<Article> {
    return map { it.toDomainModel() }
}
