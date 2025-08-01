package com.app.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsapp.domain.GetNewsUseCase
import com.app.newsapp.domain.model.Article
import com.app.newsapp.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) :
    ViewModel() {

    private val _newsListFlow = MutableStateFlow(emptyList<Article>())
    private val _loadingFlow = MutableStateFlow(false)
    private val _errorFlow = MutableStateFlow<String?>(null)

    val newsListUIStateFlow =
        combine(_newsListFlow, _loadingFlow, _errorFlow) { newsList, isLoading, error ->
            NewsListUIState(
                newsList = newsList,
                isLoading = isLoading,
                error = error
            )
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L),
            NewsListUIState()
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _loadingFlow.value = true
            _errorFlow.value = null

            when (val result = getNewsUseCase()) {
                is Result.Success -> {
                    _newsListFlow.value = result.data
                }
                is Result.Error -> {
                    _errorFlow.value = result.exception.message
                }
                is Result.Loading -> {
                    // Loading state is already handled above
                }
            }
            _loadingFlow.value = false
        }
    }
}

data class NewsListUIState(
    val newsList: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)