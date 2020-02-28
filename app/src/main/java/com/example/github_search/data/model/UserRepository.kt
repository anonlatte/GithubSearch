package com.example.github_search.data.model

import com.example.github_search.data.model.api.GitHubService

class UserRepository {
    private val client = GitHubService.create()
    suspend fun getUsers(query: String) = client.getUsers(query)
}
