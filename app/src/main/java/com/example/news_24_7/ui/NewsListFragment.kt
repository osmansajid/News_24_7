package com.example.news_24_7.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.news_24_7.R
import com.example.news_24_7.adapters.NewsAdapter
import com.example.news_24_7.databinding.FragmentNewsListBinding
import com.example.news_24_7.model.NewsItem
import com.example.news_24_7.viewmodel.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment: Fragment(R.layout.fragment_news_list),NewsAdapter.OnClickListener {

    private val viewModel by viewModels<NewsListViewModel>()
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsListBinding.bind(view)
        val adapter = NewsAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        viewModel.news.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(item: NewsItem) {
        val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(item)
        findNavController().navigate(action)
    }
}