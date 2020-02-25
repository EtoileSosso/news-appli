package com.example.news.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.dataclasses.Article
import com.example.news.dataclasses.Source
import kotlinx.android.synthetic.main.article_detail_fragment.*

class ArticleDetailFragment : Fragment() {
    private val article: Article by lazy {
        arguments?.getParcelable(ARTICLE) ?: Article(
            source = Source("", ""),
            author = "",
            title = "",
            description = "",
            url = "",
            urlToImage = "",
            publishedAt = "",
            content = ""
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.text = article.title
        source.text = getString(R.string.source, article.url, article.source.name)
        article_content.text = article.content
        article_author.text = article.author
        Glide.with(article_image)
            .load(article.urlToImage).centerCrop().placeholder(R.drawable.article_image)
            .into(article_image)

        getFavoriteText()
        getLikeText()

        goTo.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(article.url)
            startActivity(openURL)
        }

        favorite.setOnClickListener {
            article.isFavorite = !article.isFavorite
            getFavoriteText()
        }

        like.setOnClickListener {
            article.isLiked = !article.isLiked
            getLikeText()
        }

        share.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    private fun getFavoriteText() {
        favorite.text = if (article.isFavorite) {
            getString(R.string.favorite_remove)
        } else {
            getString(R.string.favorite)
        }
    }

    private fun getLikeText() {
        like.text = if (article.isLiked) {
            getString(R.string.unlike)
        } else {
            getString(R.string.like)
        }
    }

    companion object {
        private const val ARTICLE = "ARTICLE"
        fun newInstance(article: Article): ArticleDetailFragment {
            return ArticleDetailFragment().apply {
                arguments = bundleOf((ARTICLE to article))
            }
        }
    }
}
