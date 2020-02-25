package com.example.news.repositories

import com.example.news.dataclasses.Article
import com.example.news.datasources.RemoteDataSource

class ArticleRepository {
    private val online = RemoteDataSource()

    fun getArticles(): List<Article> {
        return online.getRemoteArticles()
    }
}