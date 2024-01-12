package com.project.infleran_booksearchapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import com.project.infleran_booksearchapp.data.api.RetrofitInstance
import com.project.infleran_booksearchapp.data.db.BookSearchDatabase
import com.project.infleran_booksearchapp.data.model.Book
import com.project.infleran_booksearchapp.data.model.SearchResponse
import com.project.infleran_booksearchapp.data.repository.BookSearchRepositoryImpl.PreferenceKeys.SORT_MODE
import com.project.infleran_booksearchapp.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

class BookSearchRepositoryImpl(
    private val db: BookSearchDatabase,
    private val dataStore: DataStore<Preferences>
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

    //    override fun getFavoriteBooks(): LiveData<List<Book>> {
    override fun getFavoriteBooks(): Flow<List<Book>> {
        return db.bookSearchDao().getFavoriteBooks()
    }

    // DataStore
    private object PreferenceKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs: Preferences ->
                prefs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }

}