package com.example.github_search.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.github_search.app.data.model.ApiResponse
import com.example.github_search.app.repository.UserRepository

class SearchViewModel : ViewModel() {

    var lastQuery: String = ""
        private set

    fun getUserLiveData(query: String, page: Int): LiveData<ApiResponse> {
        lastQuery = query
        return liveData {
            val receivedUsers = UserRepository.getUsers(query, page)
            emit(receivedUsers)
        }
    }
}