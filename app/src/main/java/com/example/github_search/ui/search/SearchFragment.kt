package com.example.github_search.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.github_search.R
import com.example.github_search.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels()
    private var adapter: UserRecyclerViewAdapter = UserRecyclerViewAdapter { user ->
        findNavController().navigate(
            SearchFragmentDirections.actionSearchToProfile(user.login)
        )
    }

    private var pagination = Pagination(0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUsers.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean =
                if (query != null) {
                    showUsers(query, 1)
                    true
                } else {
                    false
                }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        showUsers("test", 1)
        toggleButtons()

        binding.btnNext.setOnClickListener {
            pagination.currentPage += 1
            showUsers(viewModel.lastQuery, pagination.currentPage, false)
        }
        binding.btnPrevious.setOnClickListener {
            pagination.currentPage -= 1
            showUsers(viewModel.lastQuery, pagination.currentPage, false)
        }
    }

    private fun toggleButtons() {
        when {
            pagination.currentPage >= 1 && pagination.currentPage < pagination.lastPage -> {
                binding.btnNext.isEnabled = true
            }
            else -> {
                binding.btnNext.isEnabled = false
            }
        }
        binding.btnPrevious.isEnabled = pagination.currentPage > 1

        // Change feedback about pages
        binding.tvPagesLeft.text = getString(
            R.string.content_pages_amount,
            pagination.currentPage,
            pagination.lastPage
        )
    }

    private fun showUsers(query: String, page: Int, isNewQuery: Boolean = true) {
        viewModel.getUserLiveData(query, page).observe(viewLifecycleOwner) {
            adapter.submitList(it.users)
            pagination = if (isNewQuery) {
                Pagination(it.amount)
            } else {
                pagination
            }
            toggleButtons()
            Log.v("Request", "Were found ${it.amount} users.")
        }
    }
}