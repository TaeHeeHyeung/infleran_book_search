package com.project.infleran_booksearchapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.infleran_booksearchapp.databinding.FragmentFavoriteBinding
import com.project.infleran_booksearchapp.ui.adapter.BookSearchPagingAdapter
import com.project.infleran_booksearchapp.util.collectionLastStateFlow
import com.qualitybitz.booksearchapp.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

//    private lateinit var bookSearchViewModel: BookSearchViewModel
//    private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()

    //    private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel
        setupRecyclerView()
        setupTouchHelper(view)

//        bookSearchViewModel.favoriteBooks().observe(viewLifecycleOwner) {
//            bookSearchAdapter.submitList(it)
//        }


        //Flow
//        lifecycleScope.launch {
//            bookSearchViewModel.favoriteBooks().collectLatest {
//                bookSearchAdapter.submitList(it)
//            }
//        }

        //StateFlow
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                bookSearchViewModel.favoriteBooks().collectLatest {
//                    bookSearchAdapter.submitList(it)
//                }
//            }
//        }

        //remove boilerplate code
//        collectionLastStateFlow(bookSearchViewModel.favoriteBooks()) {
//            bookSearchAdapter.submitList(it)
//        }
        collectionLastStateFlow(favoriteViewModel.favoritePagingBook) {
            bookSearchAdapter.submitData(it)
        }

    }

    private fun setupRecyclerView() {
//        bookSearchAdapter = BookSearchAdapter()
        bookSearchAdapter = BookSearchPagingAdapter()

        binding.rvFavoriteBook.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = bookSearchAdapter
        }

        bookSearchAdapter.setOnItemClickListener {
            val action = FavoriteFragmentDirections.actionFragmentFavoriteToFragmentBook(it)
            findNavController().navigate(action)
        }
    }

    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val book = bookSearchAdapter.currentList[viewHolder.bindingAdapterPosition]
//                bookSearchViewModel.deleteBook(book)
//                Snackbar.make(view, "item has deleted", Snackbar.LENGTH_SHORT).apply {
//                    setAction("Undo") {
//                        bookSearchViewModel.saveBook(book)
//                    }
//                }.show()
                val position = viewHolder.bindingAdapterPosition
                val pagedBook = bookSearchAdapter.peek(position)
                pagedBook?.let {
                    favoriteViewModel.deleteBook(pagedBook)
                    Snackbar.make(view, "item has deleted", Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo") {
                            favoriteViewModel.saveBook(pagedBook)
                        }
                    }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteBook)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}