package com.example.github_search.data.model.api

import com.example.github_search.data.model.ApiResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/users")
    fun getUsers(@Query("q") query: String): Call<ApiResponse>

    companion object Factory {
        fun create(): GitHubService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GitHubService::class.java)
        }
    }
}