package com.example.myapplication.news

import com.example.myapplication.networcalling.ArticleEntity
import com.example.myapplication.networcalling.NewsDatabase
import com.example.myapplication.networcalling.RetrofitInstance
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.networcalling.NetworkUtils


class NewsActivity : AppCompatActivity()  {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var offlineText: TextView

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(
            NewsRepository(
                applicationContext,
                NewsDatabase.getDatabase(this).newsDao(),
                RetrofitInstance.api
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        offlineText = findViewById(R.id.offlineText)

        newsViewModel.newsList.observe(this) { newsList ->
            updateUI(newsList)
        }

        newsViewModel.fetchNews()

        if (!NetworkUtils.isInternetAvailable(this)) {
            Toast.makeText(this, "No internet connection! Showing offline data.", Toast.LENGTH_LONG).show()
            offlineText.visibility = TextView.VISIBLE
        } else {
            offlineText.visibility = TextView.GONE
        }
    }

    private fun updateUI(newsList: List<ArticleEntity>) {
        newsAdapter = NewsAdapter(newsList,this)

        recyclerView.adapter = newsAdapter
    }
}


