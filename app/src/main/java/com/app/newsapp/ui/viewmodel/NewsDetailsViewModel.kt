package com.app.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsapp.domain.GetNewsUseCase
import com.app.newsapp.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    private val _articleFlow = MutableStateFlow<Article?>(null)
    private val _loadingFlow = MutableStateFlow(false)
    private val _errorFlow = MutableStateFlow<String?>(null)

    val uiStateFlow = _articleFlow.map { article ->
        NewsDetailUiState(
            article = article,
            isLoading = _loadingFlow.value,
            error = _errorFlow.value
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000),
        NewsDetailUiState()
    )

    fun setArticle(article: Article) {
        _articleFlow.value = article
    }
}

data class NewsDetailUiState(
    val article: Article? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)