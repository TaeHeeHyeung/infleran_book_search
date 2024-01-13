package com.project.infleran_booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.project.infleran_booksearchapp.data.model.Book
import com.project.infleran_booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchPagingAdapter : PagingDataAdapter<Book, BookSearchViewHolder>(BookDiffCallback) {
    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
//        val book = currentList[position]
//        holder.bind(book)
//        holder.itemView.setOnClickListener {
//            onItemClickListener?.let { it(book) }
//        }
        val pagedBook = getItem(position)
        pagedBook?.let {
            holder.bind(pagedBook)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(pagedBook) }
            }
        }
    }

    // 아이템 클릭시 부모 뷰에 데이터 반환 하기위한 리스너
    private var onItemClickListener: ((Book) -> Unit)? = null
    fun setOnItemClickListener(listener: ((Book) -> Unit)) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
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