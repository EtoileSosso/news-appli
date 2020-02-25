package com.example.news.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(val id: String, val name: String) : Parcelable

@Parcelize
data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    var isLiked: Boolean = false,
    var isFavorite: Boolean = false
) :
    Parcelable

data class ArticleJSON(val status: String, val totalResults: Int, val articles: List<Article>)