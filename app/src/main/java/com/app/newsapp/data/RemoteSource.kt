package com.app.newsapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import javax.inject.Inject

class RemoteSource @Inject constructor(private val client: HttpClient) {

    suspend fun getNews(): NewsResponse {
        val response =
            client.get("https://newsapi.org/v2/top-headlines?country=us&apiKey=8f8c285bcbce4217902515bede564a74")
        if (response.status.isSuccess()) {
            // Handle the successful response
            val newsResponse = response.body<NewsResponse>()
            if (newsResponse.status == "ok") {
                return newsResponse
            } else {
                // Handle the case where the status is not "ok"
                throw Exception("Error fetching news: ${newsResponse.status}")
            }
        } else {
            // Handle the error response
            throw Exception("Error fetching news: ${response.status}")
        }
    }
}