package com.example.news_24_7.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.news_24_7.constants.Constants
import com.example.news_24_7.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext

class EntertainmentNewsListViewModel @ViewModelInject constructor(repository: NewsRepository,  @ApplicationContext appContext: Context,@androidx.hilt.Assisted savedState: SavedStateHandle
): ViewModel() {
    companion object {
        const val CURRENT_QUERY_STRING = "Hollywood"
    }
    private val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val news = repository.searchNews(query = CURRENT_QUERY_STRING,country = "",language = sharedPreferences.getString(Constants.LANGUAGE_CODE,"en")!!).cachedIn(viewModelScope)

}