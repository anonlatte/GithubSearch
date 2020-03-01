package com.example.github_search.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.github_search.data.model.User
import com.example.github_search.data.model.UserRepository
import kotlinx.coroutines.Dispatchers
import retrofit2.Call

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository()

    fun getUserInfoLiveData(username: String): LiveData<Call<User>> {
        return liveData(Dispatchers.IO) {
            val receivedUser = userRepository.getUserInfo(username)
            emit(receivedUser)
        }
    }
}