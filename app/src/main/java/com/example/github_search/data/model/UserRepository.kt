package com.example.github_search.data.model

import com.example.github_search.data.model.api.GitHubService

class UserRepository {
    private val client = GitHubService.create()
    suspend fun getUsers(query: String, page: Int) = client.getUsers(query, page)

    suspend fun getUserInfo(username: String) = client.getUserInfo(username)
}
