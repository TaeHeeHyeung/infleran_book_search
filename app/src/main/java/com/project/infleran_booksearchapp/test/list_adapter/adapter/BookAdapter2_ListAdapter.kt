package com.project.infleran_booksearchapp.test.list_adapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.infleran_booksearchapp.databinding.BookItemBinding
import com.project.infleran_booksearchapp.test.DiffUtilTestActivity

data class Data(val id: String)
class BookAdapter2_ListAdatper() : ListAdapter<Data, BookHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
}

class BookHolder(itemView: BookItemBinding) : RecyclerView.ViewHolder(itemView.root) {
    fun bind(data: Data?) {

    }

}