package com.example.github_search.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.github_search.R
import com.example.github_search.data.model.User
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class UserProfile : AppCompatActivity() {

    private lateinit var request: Request
    private lateinit var url: URL
    private lateinit var bmp: Bitmap
    private lateinit var userViewModel: UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        userViewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        val client = OkHttpClient()

        val userLogin = intent.getStringExtra("usersLogin")
        userViewModel.getUserInfoLiveData(userLogin!!).observe(this, Observer {
            it.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()!!
                        name.text = user.name
                        blog.text = user.blog
                        location.text = user.location
                        bio.text = user.bio
                        url = URL(user.avatarUrl)
                        GlobalScope.launch {
                            request = Request.Builder().url(url).build()
                            bmp =
                                BitmapFactory.decodeStream(client.newCall(request).execute().body()!!.byteStream())
                            runOnUiThread {
                                imageView.setImageBitmap(bmp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.v("Request", "error")
                }
            })

        })
    }
}
