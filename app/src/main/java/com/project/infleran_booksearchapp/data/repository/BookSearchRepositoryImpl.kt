package com.project.infleran_booksearchapp.data.repository

import com.project.infleran_booksearchapp.data.api.RetrofitInstance
import com.project.infleran_booksearchapp.data.model.SearchResponse
import retrofit2.Response

class BookSearchRepositoryImpl : BookSearchRepository {
    override suspend fun searchBooks(query: String, sort: String, page: Int, size: Int): Response<SearchResponse> {
        return RetrofitInstance.api.searchBooks(query, sort, page, size)
    }
}