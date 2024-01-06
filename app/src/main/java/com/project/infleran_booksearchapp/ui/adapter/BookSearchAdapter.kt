package com.project.infleran_booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.project.infleran_booksearchapp.data.model.Book
import com.project.infleran_booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchAdapter() : ListAdapter<Book, BookSearchViewHolder>(BookDiffCallback) {
    // 데이터 변경시 추가 하여 알림 (ListAdapter 기능)
//    override fun submitList(list: MutableList<Book>?) {
//        super.submitList(list)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val book = currentList[position]
        holder.bind(book)
    }

    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }


}