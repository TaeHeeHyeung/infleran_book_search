package com.project.infleran_booksearchapp.data.repository

import com.project.infleran_booksearchapp.data.model.SearchResponse
import retrofit2.Response

interface BookSearchRepository {

    suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<SearchResponse>
}