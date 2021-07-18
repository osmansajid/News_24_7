package com.example.news_24_7.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.news_24_7.model.NewsApi
import com.example.news_24_7.model.NewsPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

    fun searchNews(query: String) =
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    maxSize = 80,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { NewsPagingSource(newsApi, query) }
            ).liveData
}