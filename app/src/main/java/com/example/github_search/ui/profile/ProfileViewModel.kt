package com.example.github_search.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.github_search.app.data.model.UserResponse
import com.example.github_search.app.repository.UserRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    fun getUserInfoLiveData(username: String): LiveData<UserResponse> {
        return liveData {
            val receivedUser = UserRepository.getUserInfo(username)
            emit(receivedUser)
        }
    }
}