package com.project.infleran_booksearchapp.data.repository

import androidx.lifecycle.LiveData
import com.project.infleran_booksearchapp.data.api.RetrofitInstance
import com.project.infleran_booksearchapp.data.db.BookSearchDatabase
import com.project.infleran_booksearchapp.data.model.Book
import com.project.infleran_booksearchapp.data.model.SearchResponse
import retrofit2.Response

class BookSearchRepositoryImpl(
    private val db: BookSearchDatabase
) : BookSearchRepository {


    override suspend fun searchBooks(query: String, sort: String, page: Int, size: Int)
            : Response<SearchResponse> {
        return RetrofitInstance.api.searchBooks(query, sort, page, size)
    }

    override suspend fun insertBook(book: Book) {
        db.bookSearchDao().insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        db.bookSearchDao().deleteBook(book)
    }

    override fun getFavoriteBooks(): LiveData<List<Book>> {
        return db.bookSearchDao().getFavoriteBooks()
    }
}