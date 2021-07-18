package com.example.news_24_7.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class NewsItem(
    val source: NewsSource,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String?
) : Parcelable {

    @kotlinx.parcelize.Parcelize
    data class NewsSource(
        val name: String
    ) : Parcelable
}
