package com.app.newsapp.domain.model

data class Article(
    val id: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val sourceName: String?
) {
    fun getPublishedAtFormatted(): String {
        return publishedAt.replace("T", " ").replace("Z", "")
    }
}
