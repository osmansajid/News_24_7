package com.example.news_24_7.model

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news_24_7.constants.Constants
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(private val newsApi: NewsApi, private val query: String,private val country: String,private val language: String): PagingSource<Int, NewsItem>() {
    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val pageNum = params.key ?: 1

        return try {
            val response = if(query == ""){
                newsApi.getTopNews(country,params.loadSize, pageNum)
            }else{
                newsApi.searchNews(query,language,"publishedAt",params.loadSize,pageNum)
            }
           // if(response.articles.isEmpty())Log.d("LoadedOKOK","Empty")
            val articles = response.articles
            LoadResult.Page(
                articles,
                if(pageNum == 1) null else pageNum - 1,
                if(articles.isEmpty()) null else pageNum + 1
            )
        }catch (e: HttpException){
            LoadResult.Error(e)
        }catch (e: IOException){
            LoadResult.Error(e)
        }
    }
}