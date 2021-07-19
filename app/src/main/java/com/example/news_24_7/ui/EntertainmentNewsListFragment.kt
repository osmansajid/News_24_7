package com.example.news_24_7.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import com.example.news_24_7.R
import com.example.news_24_7.adapters.NewsAdapter
import com.example.news_24_7.adapters.NewsHeaderFooterAdapter
import com.example.news_24_7.databinding.FragmentEntertainmentNewsListBinding
import com.example.news_24_7.databinding.FragmentNewsListBinding
import com.example.news_24_7.model.NewsItem
import com.example.news_24_7.viewmodel.EntertainmentNewsListViewModel
import com.example.news_24_7.viewmodel.NewsListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntertainmentNewsListFragment : Fragment(R.layout.fragment_entertainment_news_list), NewsAdapter.OnClickListener {

    private val viewModel by viewModels<EntertainmentNewsListViewModel>()
    private var _binding: FragmentEntertainmentNewsListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Entertainment", "onViewCreated: ")

        //showing bottom bar for news list fragment
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_bar)
        navBar.isVisible = true

        _binding = FragmentEntertainmentNewsListBinding.bind(view)
        val adapter = NewsAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsHeaderFooterAdapter { adapter.retry() },
                footer = NewsHeaderFooterAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.news.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener {
            binding.apply {
                recyclerView.itemAnimator = null
                progressBarCircle.isVisible = it.source.refresh is LoadState.Loading
                buttonRetry.isVisible = it.source.refresh is LoadState.Error
                textViewFail.isVisible = it.source.refresh is LoadState.Error
                recyclerView.isVisible = it.source.refresh is LoadState.NotLoading

                if (it.source.refresh is LoadState.NotLoading && adapter.itemCount < 1 && it.append.endOfPaginationReached) {
                    textViewNotFound.isVisible = true
                    recyclerView.isVisible = false
                } else {
                    textViewNotFound.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_menu,menu)

        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    searchView.clearFocus()
                    val action = EntertainmentNewsListFragmentDirections.actionEntertainmentNewsListFragmentToSearchNewsListFragment(query)
                    findNavController().navigate(action)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(item: NewsItem) {
        val action = EntertainmentNewsListFragmentDirections.actionEntertainmentNewsListFragmentToNewsDetailsFragment(item)
        findNavController().navigate(action)
    }
}