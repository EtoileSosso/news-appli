package com.example.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.dataclasses.Article

class ArticleAdapter(val articleClicked: (item: Article) -> Unit) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    private val _dataSet: MutableList<Article> = mutableListOf()

    inner class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(item: Article) {
            val txtTitle = root.findViewById<TextView>(R.id.article_title)
            val txtDesc = root.findViewById<TextView>(R.id.article_description)
            val articleImage = root.findViewById<ImageView>(R.id.preview_image)

            txtTitle.text = item.title
            txtDesc.text = item.description
            Glide.with(root.context)
                .load(item.urlToImage)
                .into(articleImage)
            root.setOnClickListener {
                articleClicked(item)
            }
        }
    }

    fun updateData(list: List<Article>) {
        _dataSet.clear()
        _dataSet.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_preview, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_dataSet[position])
    }

    override fun getItemCount() = _dataSet.size
}