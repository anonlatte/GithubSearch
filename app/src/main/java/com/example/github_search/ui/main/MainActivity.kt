package com.example.github_search.ui.main

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setHasFixedSize(true)

        adapter = UserRecyclerViewAdapter(this@MainActivity, userArrayList)
        list.adapter = adapter

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showUsers(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        showUsers("tom")
    }

    private fun showUsers(query: String) {
        mainViewModel.getUserLiveData(query).observe(this, Observer {
            it.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    userArrayList.clear()
                    userArrayList.addAll(response.body()!!.usersList)
                    adapter.notifyDataSetChanged()
                    Log.v("Request: ", call.request().url().toString())
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.v("Request", t.message!!)
                }

            })
        })
    }
}
