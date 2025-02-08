package com.example.myapplication.model


import kotlinx.parcelize.Parcelize
import android.os.Parcelable
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
@Parcelize
data class Article(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val url: String
):Parcelable
