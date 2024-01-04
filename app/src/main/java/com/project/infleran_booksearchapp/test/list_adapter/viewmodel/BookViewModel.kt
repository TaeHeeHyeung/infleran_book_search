package com.project.infleran_booksearchapp.test.list_adapter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.infleran_booksearchapp.test.list_adapter.BookData

class BookViewModel() : ViewModel() {
    private val bookData_: MutableLiveData<List<BookData>> = MutableLiveData<List<BookData>>()
    val bookData: MutableLiveData<List<BookData>>
        get() = bookData_


}