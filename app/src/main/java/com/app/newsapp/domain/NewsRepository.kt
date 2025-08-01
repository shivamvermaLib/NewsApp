package com.app.newsapp.domain

import com.app.newsapp.domain.model.Article
import com.app.newsapp.domain.model.Result

interface NewsRepository {
    suspend fun getNews(): Result<List<Article>>
}
