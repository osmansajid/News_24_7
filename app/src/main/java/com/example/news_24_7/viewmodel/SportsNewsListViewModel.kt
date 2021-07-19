package com.example.news_24_7.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.news_24_7.repository.NewsRepository

class SportsNewsListViewModel @ViewModelInject constructor(repository: NewsRepository, @androidx.hilt.Assisted savedState: SavedStateHandle
): ViewModel() {
    companion object{
        const val CURRENT_QUERY_STRING = "Sports"
    }

    val news = repository.searchNews(query = CURRENT_QUERY_STRING).cachedIn(viewModelScope)

}