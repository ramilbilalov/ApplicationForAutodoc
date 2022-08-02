package com.bilalov.testapplicationforappricot.data.request

data class ResponseSearchRepos(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)