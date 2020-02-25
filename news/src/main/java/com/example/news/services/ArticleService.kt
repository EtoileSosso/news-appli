package com.example.news.services

import com.example.news.dataclasses.ArticleJSON
import retrofit2.Call
import retrofit2.http.GET

interface ArticleService {
    @GET("/v2/top-headlines?apiKey=d78ba2c717b1405e960d6530ed8780ac&country=us")
    fun getArticles(): Call<ArticleJSON>
}