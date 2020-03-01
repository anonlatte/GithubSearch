package com.example.github_search.ui.main

import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.github_search.R
import com.example.github_search.data.model.ApiResponse
import com.example.github_search.data.model.User
import com.example.github_search.data.model.UserRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private val userArrayList: ArrayList<User> = ArrayList()
    private lateinit var adapter: UserRecyclerViewAdapter
    private lateinit var pagination: Pagination
    private var userQuery = "team"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setHasFixedSize(true)

        adapter = UserRecyclerViewAdapter(this@MainActivity, userArrayList)
        list.adapter = adapter

        // Initialize pagination
        pagination = Pagination(0)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showUsers(query!!, 1)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        // Test initialization
        showUsers("anonlatte", 1)
        toggleButtons()

        nextButton.setOnClickListener {
            pagination.currentPage += 1
            showUsers(userQuery, pagination.currentPage)
        }
        prevButton.setOnClickListener {
            pagination.currentPage -= 1
            showUsers(userQuery, pagination.currentPage)
        }
    }

    private fun toggleButtons() {
        if (pagination.currentPage >= 1 && pagination.currentPage < pagination.lastPage) {
            nextButton.isEnabled = true
        } else if (pagination.currentPage < 1 || pagination.currentPage >= pagination.lastPage) {
            nextButton.isEnabled = false
        }
        prevButton.isEnabled = pagination.currentPage > 1

        // Change feedback about pages
        pagesLeft.text = SpannableString("Page ${pagination.currentPage} of ${pagination.lastPage}")
    }

    private fun showUsers(query: String, page: Int) {
        mainViewModel.getUserLiveData(query, page).observe(this, Observer {
            it.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.body() != null) {
                        userArrayList.clear()
                        userArrayList.addAll(response.body()!!.usersList)
                        adapter.notifyDataSetChanged()

                        // Reinitialize pagination store
                        if (userQuery != query) {
                            userQuery = query
                            pagination = Pagination(response.body()!!.count)
                        }
                        toggleButtons()
                        Log.v("Request", call.request().url().toString())
                        Log.v("Request", "Were found ${response.body()!!.count} users.")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.v("Request", t.message!!)
                }
            })
        })
    }
}