package com.example.news_24_7.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.news_24_7.constants.Constants
import com.example.news_24_7.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext

class SearchNewsListViewModel @ViewModelInject constructor(repository: NewsRepository, @ApplicationContext appContext: Context, @androidx.hilt.Assisted savedState: SavedStateHandle
): ViewModel() {
    companion object{
        const val LAST_QUERY_STRING = "last_query"
        const val CURRENT_QUERY_STRING = "@@@@ThisIsDefaultQueryShouldNotReturnResult@@@@"
    }
    private val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val query = savedState.getLiveData(LAST_QUERY_STRING, CURRENT_QUERY_STRING)

    val news = query.switchMap {qString->
        repository.searchNews(query = qString,"",sharedPreferences.getString(Constants.LANGUAGE_CODE,"en")!!)
    }.cachedIn(viewModelScope)

    fun searchNews(newQuery: String){
        query.value = newQuery
    }
}