package com.example.myapplication.networcalling

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Entity(tableName = "articles")
@Parcelize

data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val url: String
) : Parcelable
