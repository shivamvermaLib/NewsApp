package com.app.newsapp.domain

import com.app.newsapp.data.NewsRepository
import com.app.newsapp.domain.model.Article
import com.app.newsapp.domain.model.Result
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(): Result<List<Article>> = repository.getNews()
}
