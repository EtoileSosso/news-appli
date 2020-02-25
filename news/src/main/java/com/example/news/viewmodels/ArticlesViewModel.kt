package com.example.news.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.dataclasses.Article
import com.example.news.repositories.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesViewModel : ViewModel() {
    private val repository: ArticleRepository = ArticleRepository()
    private val _listArticles = MutableLiveData<List<Article>>()

    val listArticle: LiveData<List<Article>>
        get() = _listArticles

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getArticles()
            _listArticles.postValue(result)
        }
    }
}