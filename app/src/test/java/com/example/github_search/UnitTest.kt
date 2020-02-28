package com.example.github_search

import androidx.lifecycle.liveData
import com.example.github_search.data.model.api.GitHubService
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun getTestUsers() {
        val apiService = GitHubService.create()
        val testUsers = liveData(Dispatchers.IO) {
            val receivedUsers = apiService.getUsers("anonlatte")
            emit(receivedUsers)
        }

        Assert.assertEquals(
            """{
  "total_count": 1,
  "incomplete_results": false,
  "items": [
    {
      "login": "anonlatte",
      "id": 16625266,
      "node_id": "MDQ6VXNlcjE2NjI1MjY2",
      "avatar_url": "https://avatars1.githubusercontent.com/u/16625266?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/anonlatte",
      "html_url": "https://github.com/anonlatte",
      "followers_url": "https://api.github.com/users/anonlatte/followers",
      "following_url": "https://api.github.com/users/anonlatte/following{/other_user}",
      "gists_url": "https://api.github.com/users/anonlatte/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/anonlatte/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/anonlatte/subscriptions",
      "organizations_url": "https://api.github.com/users/anonlatte/orgs",
      "repos_url": "https://api.github.com/users/anonlatte/repos",
      "events_url": "https://api.github.com/users/anonlatte/events{/privacy}",
      "received_events_url": "https://api.github.com/users/anonlatte/received_events",
      "type": "User",
      "site_admin": false,
      "score": 1.0
    }
  ]
}""", testUsers
        )
    }
}
