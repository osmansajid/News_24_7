package com.example.news_24_7.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.news_24_7.repository.NewsRepository

class SearchNewsListViewModel @ViewModelInject constructor(repository: NewsRepository, @androidx.hilt.Assisted savedState: SavedStateHandle
): ViewModel() {
    companion object{
        const val LAST_QUERY_STRING = "last_query"
        const val CURRENT_QUERY_STRING = "@@@@ThisIsDefaultQueryShouldNotReturnResult@@@@"
    }

    private val query = savedState.getLiveData(LAST_QUERY_STRING, CURRENT_QUERY_STRING)

    val news = query.switchMap {qString->
        repository.searchNews(query = qString)
    }.cachedIn(viewModelScope)

    fun searchNews(newQuery: String){
        query.value = newQuery
    }
}