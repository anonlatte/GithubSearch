package com.example.github_search.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    @Expose
    val login: String,

    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("html_url")
    @Expose
    val html_url: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("blog")
    @Expose
    val blog: String,

    @SerializedName("location")
    @Expose
    val location: String,

    @SerializedName("bio")
    @Expose
    val bio: String
)