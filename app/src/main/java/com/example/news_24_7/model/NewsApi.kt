package com.example.news_24_7.model

import com.example.news_24_7.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    companion object{
        const val API_KEY = BuildConfig.NEWS_API_ACCESS_KEY
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("country")country: String,
        @Query("pageSize") perPage: Int,
        @Query("page") page: Int
    ): NewsApiResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") perPage: Int,
        @Query("page") page: Int
    ): NewsApiResponse
}