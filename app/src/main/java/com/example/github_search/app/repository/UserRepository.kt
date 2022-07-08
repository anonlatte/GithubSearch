package com.example.github_search.app.repository

import com.example.github_search.app.data.model.ApiResponse
import com.example.github_search.app.data.model.UserResponse
import com.example.github_search.app.network.api.GitHubServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRepository {

    suspend fun getUsers(query: String, page: Int): ApiResponse = withContext(Dispatchers.IO) {
        GitHubServiceImpl.gitHubServiceImpl.getUsers(query, page)
    }

    suspend fun getUserInfo(username: String): UserResponse = withContext(Dispatchers.IO) {
        GitHubServiceImpl.gitHubServiceImpl.getUserInfo(username)
    }
}