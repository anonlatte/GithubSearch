package com.example.github_search.ui.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.example.github_search.R
import com.example.github_search.app.data.model.UserResponse
import com.example.github_search.databinding.FragmentProfileBinding

class ProfileFragment : AppCompatActivity() {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val userViewModel: ProfileViewModel by viewModels()
    private val userLogin: String by lazy { intent.getStringExtra("usersLogin") ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        if (userLogin.isNotEmpty()) {
            userViewModel.getUserInfoLiveData(userLogin)
                .observe(this, ::initProfile)
        }
    }

    private fun initProfile(user: UserResponse) = with(binding) {
        tvName.text = user.name
        tvBlog.text = user.blog
        tvLocation.text = user.location
        tvBio.text = user.bio
        ivAvatar.load(user.avatarUrl) {
            transformations(CircleCropTransformation())
        }
    }
}