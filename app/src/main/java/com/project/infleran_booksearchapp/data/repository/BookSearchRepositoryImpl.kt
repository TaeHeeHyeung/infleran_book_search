package com.project.infleran_booksearchapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.project.infleran_booksearchapp.data.api.RetrofitInstance
import com.project.infleran_booksearchapp.data.db.BookSearchDatabase
import com.project.infleran_booksearchapp.data.model.Book
import com.project.infleran_booksearchapp.data.model.SearchResponse
import com.project.infleran_booksearchapp.data.repository.BookSearchRepositoryImpl.PreferenceKeys.CACHE_DELETE_MODE
import com.project.infleran_booksearchapp.data.repository.BookSearchRepositoryImpl.PreferenceKeys.SORT_MODE
import com.project.infleran_booksearchapp.util.Constants.PAGING_SIZE
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
        val CACHE_DELETE_MODE = booleanPreferencesKey("cache_delete_mode")
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

    override suspend fun saveCacheDeleteMode(mode: Boolean) {
        dataStore.edit { prefs ->
            prefs[CACHE_DELETE_MODE] = mode
        }
    }

    override suspend fun getCacheDeleteMode(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[CACHE_DELETE_MODE] ?: false
            }
    }


    // Paging
    override fun getFavoritePagingBooks(): Flow<PagingData<Book>> {
        val pagingSourceFactory = { db.bookSearchDao().getFavoritePagingBooks() }
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchBookPaging(query: String, sort: String): Flow<PagingData<Book>> {
        val pagingSourceFactory = { BookSearchPagingSource(query, sort) }
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}