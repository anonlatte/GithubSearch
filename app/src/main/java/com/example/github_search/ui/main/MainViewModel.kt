package com.example.github_search.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.github_search.data.model.ApiResponse
import com.example.github_search.data.model.UserRepository
import kotlinx.coroutines.Dispatchers
import retrofit2.Call

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository()

    fun getUserLiveData(query: String): LiveData<Call<ApiResponse>> {
        return liveData(Dispatchers.IO) {
            val receivedUsers = userRepository.getUsers(query)
            emit(receivedUsers)
        }
    }
}