package com.example.news_24_7.viewmodel

import android.app.Application
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.news_24_7.NewsAndroidApplication
import com.example.news_24_7.constants.Constants
import com.example.news_24_7.repository.NewsRepository
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext


class NewsListViewModel @ViewModelInject constructor(repository: NewsRepository,@ApplicationContext appContext: Context, @androidx.hilt.Assisted savedState: SavedStateHandle
): ViewModel() {
    companion object{
        const val CURRENT_QUERY_STRING = ""
    }
    private val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    val news = repository.searchNews(query = CURRENT_QUERY_STRING,sharedPreferences.getString(Constants.COUNTRY_CODE,"us")!!).cachedIn(viewModelScope)

}