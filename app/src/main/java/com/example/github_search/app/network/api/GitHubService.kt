package com.example.github_search.app.network.api

import com.example.github_search.app.data.model.ApiResponse
import com.example.github_search.app.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit документация с примерами https://square.github.io/retrofit/
interface GitHubService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): ApiResponse

    @GET("users/{username}")
    suspend fun getUserInfo(@Path("username") username: String): UserResponse
}