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

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("html_url")
    @Expose
    val html_url: String,

    @SerializedName("followers_url")
    @Expose
    val followers_url: String,

    @SerializedName("following_url")
    @Expose
    val following_url: String,

    @SerializedName("starred_url")
    @Expose
    val starred_url: String,

    @SerializedName("gists_url")
    @Expose
    val gists_url: String,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("score")
    @Expose
    val score: Int
)