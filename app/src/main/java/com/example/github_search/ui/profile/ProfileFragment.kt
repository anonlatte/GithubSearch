package com.example.github_search.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.github_search.R
import com.example.github_search.app.data.model.UserResponse
import com.example.github_search.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val userViewModel: ProfileViewModel by viewModels()
    private val navArgs by navArgs<ProfileFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.getUserInfoLiveData(navArgs.userLogin)
            .observe(viewLifecycleOwner, ::initProfile)
    }

    private fun initProfile(user: UserResponse) = with(binding) {
        tvName.text = user.name
        tvBlog.text = user.blog
        tvLocation.text = user.location
        tvBio.text = user.bio
        ivAvatar.load(user.avatarUrl)
    }
}