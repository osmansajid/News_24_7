package com.example.news_24_7.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(private val newsApi: NewsApi, private val query: String): PagingSource<Int, NewsItem>() {
    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val pageNum = params.key?:1

        return try {
            val response = if(query == ""){
                newsApi.getTopNews("us", pageNum,params.loadSize)
            }else{
                newsApi.searchNews(query,"en","publishedAt",pageNum,params.loadSize)
            }

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