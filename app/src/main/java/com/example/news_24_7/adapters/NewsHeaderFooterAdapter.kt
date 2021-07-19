package com.example.news_24_7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news_24_7.databinding.FragmentNewsListFooterHeaderBinding

class NewsHeaderFooterAdapter(private val retry:() -> Unit): LoadStateAdapter<NewsHeaderFooterAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: FragmentNewsListFooterHeaderBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            binding.apply {
                progressBarCircle.isVisible = loadState is LoadState.Loading
                textViewRetry.isVisible = loadState !is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = FragmentNewsListFooterHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
}