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
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.example.news_24_7.R
import com.example.news_24_7.adapters.NewsAdapter
import com.example.news_24_7.adapters.NewsHeaderFooterAdapter
import com.example.news_24_7.databinding.FragmentSearchNewsListBinding
import com.example.news_24_7.databinding.FragmentSportsNewsListBinding
import com.example.news_24_7.model.NewsItem
import com.example.news_24_7.viewmodel.SearchNewsListViewModel
import com.example.news_24_7.viewmodel.SportsNewsListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsListFragment : Fragment(R.layout.fragment_search_news_list), NewsAdapter.OnClickListener {

    private val viewModel by viewModels<SearchNewsListViewModel>()
    private var _binding: FragmentSearchNewsListBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<SearchNewsListFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("SportsNewsListFragment", "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)

        //showing bottom bar for news list fragment
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_bar)
        navBar.isVisible = true

        _binding = FragmentSearchNewsListBinding.bind(view)
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

        //viewModel.searchNews(args.queryString)

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
        searchItem.expandActionView()
        val searchView = searchItem.actionView as SearchView
        searchView.requestFocus()
       // searchView.setQuery(args.queryString,false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchNews(query)
                    searchView.clearFocus()
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
        val action = SearchNewsListFragmentDirections.actionSearchNewsListFragmentToNewsDetailsFragment(item)
        findNavController().navigate(action)
    }
}