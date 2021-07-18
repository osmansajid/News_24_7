package com.example.news_24_7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.news_24_7.R
import com.example.news_24_7.databinding.FragmentNewsListBinding
import com.example.news_24_7.databinding.ItemNewsBinding
import com.example.news_24_7.model.NewsItem

class NewsAdapter(private val listener: OnClickListener): PagingDataAdapter<NewsItem,NewsAdapter.ViewHolder>(COMPARATOR) {
    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<NewsItem>(){
            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    val item = getItem(pos)
                    if(item != null){
                        listener.onClick(item)
                    }
                }
            }
        }

        fun setItem(item: NewsItem){
            binding.apply {
                Glide.with(itemView)
                    .load(item.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.news)
                    .into(imageView)

                textViewDate.text = item.publishedAt.dropLast(10)
                textViewHeading.text = item.title
                textViewSource.text = item.source!!.name
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null){
            holder.setItem(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding)
    }

    interface OnClickListener{
        fun onClick(item: NewsItem)
    }
}