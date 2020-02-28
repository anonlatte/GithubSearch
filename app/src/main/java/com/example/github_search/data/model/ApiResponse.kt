package com.example.github_search.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("total_count")
    @Expose
    val count: Int,

    @SerializedName("incomplete_results")
    @Expose
    val incompleteResults: Boolean,

    @SerializedName("items")
    @Expose
    val usersList: List<User>
)