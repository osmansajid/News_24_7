package com.example.news_24_7.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.news_24_7.R
import com.example.news_24_7.databinding.FragmentDetailNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment: Fragment(R.layout.fragment_detail_news) {
    private val args by navArgs<NewsDetailsFragmentArgs>()
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailNewsBinding.bind(view)
        binding.apply {
            val newsItem = args.newsItem

            Glide.with(this@NewsDetailsFragment)
                .load(newsItem.urlToImage)
                .error(R.drawable.news)
                .centerCrop()
                .into(imageView)

            textViewHeading.text = newsItem.title
            textViewDate.text = newsItem.publishedAt.dropLast(10)
            textViewSource.text = newsItem.source.name
            textViewDetails.text = newsItem.description
            newsItem.content?.let {
                textViewContents.text = it.dropLast(20) + "..."
                textViewContents.isVisible = true
            }
            textViewSeeMore.setOnClickListener {
                val uri = Uri.parse(newsItem.url)
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}