package com.app.newsapp.domain

import com.app.newsapp.domain.model.Article
import com.app.newsapp.domain.model.Result
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(articleId: String): Result<Article?> {
        return when (val result = repository.getNews()) {
            is Result.Success -> {
                val article = result.data.find { it.url == articleId }
                Result.Success(article)
            }
            is Result.Error -> result
            is Result.Loading -> result
        }
    }
}
