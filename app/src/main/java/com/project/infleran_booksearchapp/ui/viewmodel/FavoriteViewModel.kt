package com.qualitybitz.booksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.project.infleran_booksearchapp.data.model.Book
import com.project.infleran_booksearchapp.data.repository.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) : ViewModel() {
    // Paging
    val favoritePagingBook: StateFlow<PagingData<Book>> =
        bookSearchRepository.getFavoritePagingBooks()
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope, SharingStarted.WhileSubscribed(3000), PagingData.empty()
            )

    // Room
    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBook(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.deleteBook(book)
    }
}