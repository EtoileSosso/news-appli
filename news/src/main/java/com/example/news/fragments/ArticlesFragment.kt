package com.example.news.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.adapters.ArticleAdapter
import com.example.news.viewmodels.ArticlesViewModel
import kotlinx.android.synthetic.main.articles_list_fragment.*


class ArticlesFragment : Fragment() {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapterRecycler: ArticleAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.listArticle.observe(viewLifecycleOwner, Observer {
            adapterRecycler.updateData(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapterRecycler = ArticleAdapter(articleClicked = {
            val articleDetailFragment = ArticleDetailFragment.newInstance(article = it)
            childFragmentManager.beginTransaction().apply {
                replace(R.id.article_fragment, articleDetailFragment)
                addToBackStack(null)
            }.commit()
        })
        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.articles_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articles_list.layoutManager = LinearLayoutManager(context)
        articles_list.adapter = adapterRecycler
    }
}