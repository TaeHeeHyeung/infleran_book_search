package com.project.infleran_booksearchapp.test.list_adapter.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class BookAdapter1_DiffUtil : RecyclerView.Adapter<BookAdapter1_DiffUtil.ViewHolder_>() {
    data class Data(val id: String)


    private val mDiffCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem;
        }

    }
    private val mDiffer = AsyncListDiffer<Data>(this@BookAdapter1_DiffUtil, mDiffCallback);

    // 데이터 설정 메서드
    fun submitList(newList: List<Data>) {
        mDiffer.submitList(newList)
    }

    class ViewHolder_(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_ {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder_, position: Int) {
        TODO("Not yet implemented")
    }

}
