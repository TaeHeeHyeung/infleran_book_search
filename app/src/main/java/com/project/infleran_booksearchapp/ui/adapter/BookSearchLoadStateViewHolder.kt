package com.project.infleran_booksearchapp.ui.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.project.infleran_booksearchapp.databinding.ItemLoadStateBinding

class BookSearchLoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvError.text = "Error occurred"
        }
        val isLoading = loadState is LoadState.Loading
        val isError = loadState is LoadState.Error
        binding.progressBar.isVisible = isLoading
        binding.btnRetry.isVisible = isError
        binding.tvError.isVisible = isError
    }
}