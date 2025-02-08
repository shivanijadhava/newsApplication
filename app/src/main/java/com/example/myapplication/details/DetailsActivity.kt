package com.example.myapplication.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Article
import com.example.myapplication.networcalling.ArticleEntity

class DetailsActivity : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var tvDescription : TextView
    private lateinit var ivNewsImage: ImageView
    private lateinit var ivBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val article: ArticleEntity? = intent.getParcelableExtra("article")

       title = findViewById(R.id.tvTitle)
       tvDescription= findViewById(R.id.tvDescription)
        ivNewsImage = findViewById(R.id.ivNewsImage)
        ivBack = findViewById(R.id.ivBack)

        ivBack.setOnClickListener { onBackPressed() }
        // Set data in UI
        article?.let {
           title.text = it.title
            tvDescription.text = it.description

            Glide.with(this)
                .load(it.urlToImage)
                .into(ivNewsImage)
        }
    }
}
