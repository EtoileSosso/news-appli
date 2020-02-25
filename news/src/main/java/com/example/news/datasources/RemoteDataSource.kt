package com.example.news.datasources

import com.example.news.BuildConfig
import com.example.news.dataclasses.Article
import com.example.news.services.ArticleService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {
    private val service: ArticleService

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client (client)
            baseUrl("https://newsapi.org/")
        }.build()

        service = retrofit.create(ArticleService::class.java)
    }

    fun getRemoteArticles(): List<Article> {
        val result = service.getArticles().execute()
        return if (result.isSuccessful) {
            result.body()?.articles ?: emptyList()
        } else {
            emptyList()
        }
    }
}