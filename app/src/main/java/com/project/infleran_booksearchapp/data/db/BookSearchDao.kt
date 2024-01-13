package com.project.infleran_booksearchapp.data.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.infleran_booksearchapp.data.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM books")
//    fun getFavoriteBooks(): LiveData<List<Book>>
    fun getFavoriteBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books")
    fun getFavoritePagingBooks(): PagingSource<Int, Book>

}