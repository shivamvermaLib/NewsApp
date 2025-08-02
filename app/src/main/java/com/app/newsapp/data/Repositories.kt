package com.app.newsapp.data

import com.app.newsapp.data.mapper.toDomainModel
import com.app.newsapp.domain.model.Article
import com.app.newsapp.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : NewsRepository {

    override suspend fun getNews(): Result<List<Article>> = withContext(dispatcher) {
        try {
            val response = remoteSource.getNews()
            val domainArticles = response.articles.toDomainModel()
            Result.Success(domainArticles)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

interface NewsRepository {
    suspend fun getNews(): Result<List<Article>>
}
