package com.example.myapplication.news

import com.example.myapplication.networcalling.NewsApiService
import com.example.myapplication.networcalling.NewsDao


import android.content.Context
import android.util.Log
import com.example.myapplication.networcalling.ArticleEntity
import com.example.myapplication.networcalling.NetworkUtils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(
    private val context: Context,
    private val newsDao: NewsDao,
    private val apiService: NewsApiService
) {
    suspend fun getNews(): List<ArticleEntity> {
        return withContext(Dispatchers.IO) {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    val response = apiService.getTopHeadlines("us", "b020275192c64fa896b618a017b0135d")
                    if (response.isSuccessful) {
                        response.body()?.articles?.map { article ->
                            ArticleEntity(
                                title = article.title,
                                description = article.description,
                                urlToImage = article.urlToImage,
                                url = article.url
                            )
                        }?.also { articles ->
                            newsDao.clearArticles() // Clear old data
                            newsDao.insertArticles(articles) // Cache new data
                        } ?: emptyList()
                    } else {
                        Log.e("NewsRepository", "API Error: ${response.message()}")
                        newsDao.getArticles() // Load offline data
                    }
                } catch (e: Exception) {
                    Log.e("NewsRepository", "Failure: ${e.message}")
                    newsDao.getArticles() // Load offline data
                }
            } else {
                newsDao.getArticles() // Load offline data
            }
        }
    }
}

