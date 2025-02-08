package com.example.myapplication.news

import com.example.myapplication.networcalling.ArticleEntity


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<List<ArticleEntity>>()
    val newsList: LiveData<List<ArticleEntity>> get() = _newsList

    fun fetchNews() {
        viewModelScope.launch {
            val news = repository.getNews()
            _newsList.postValue(news)
        }
    }
}


