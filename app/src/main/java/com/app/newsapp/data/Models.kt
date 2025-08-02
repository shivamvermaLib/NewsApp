package com.app.newsapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("status") var status: String? = null,
    @SerialName("totalResults") var totalResults: Int? = null,
    @SerialName("articles") var articles: List<Article> = emptyList()
) {
    @Serializable
    data class Article(
        @SerialName("source") var source: Source? = null,
        @SerialName("author") var author: String? = null,
        @SerialName("title") var title: String? = null,
        @SerialName("description") var description: String? = null,
        @SerialName("url") var url: String? = null,
        @SerialName("urlToImage") var urlToImage: String? = null,
        @SerialName("publishedAt") var publishedAt: String? = null,
        @SerialName("content") var content: String? = null

    ) {
        @Serializable
        data class Source(
            @SerialName("id") var id: String? = null,
            @SerialName("name") var name: String? = null

        )
    }
}
