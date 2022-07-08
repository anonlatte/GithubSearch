package com.example.github_search.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.github_search.R
import com.example.github_search.databinding.FragmentSearchBinding
import com.example.github_search.ui.profile.ProfileFragment

class SearchFragment : AppCompatActivity() {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels()
    private var adapter: UserRecyclerViewAdapter = UserRecyclerViewAdapter { user ->
        val profileIntent = Intent(this, ProfileFragment::class.java)
            .putExtra("usersLogin", user.login)
        startActivity(profileIntent)
    }

    private lateinit var pagination: Pagination

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search)

        binding.rvUsers.adapter = adapter

        // Initialize pagination
        pagination = Pagination(0)

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
        viewModel.getUserLiveData(query, page).observe(this) {
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