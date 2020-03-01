package com.example.github_search.ui.main

import kotlin.math.ceil

class Pagination(totalItems: Int) {
    var totalNumItems = totalItems
    val lastPage = ceil((totalNumItems / ITEMS_PER_PAGE.toDouble())).toInt()
    var currentPage = 1

    companion object {
        private const val ITEMS_PER_PAGE = 30
    }
}