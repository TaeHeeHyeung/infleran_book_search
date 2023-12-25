package com.project.infleran_booksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.infleran_booksearchapp.databinding.FragmentSearchBinding
import com.project.infleran_booksearchapp.ui.adapter.BookSearchAdapter
import com.project.infleran_booksearchapp.ui.viewmodel.BookSearchViewModel
import com.project.infleran_booksearchapp.util.Constants


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    private lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var bookSearchAdapter: BookSearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel
        setupRecyclerView()
        searchBooks()

        bookSearchViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            val books = it.documents
            bookSearchAdapter.submitList(books)
        })

        binding.etSearch.setText(bookSearchViewModel.query)
        binding.etSearch.text = Editable.Factory.getInstance().newEditable(bookSearchViewModel.query)
    }

    private fun setupRecyclerView() {
        bookSearchAdapter = BookSearchAdapter()

        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookSearchAdapter
        }
    }

    private fun searchBooks() {
        var startTime: Long = System.currentTimeMillis()
        var endTime: Long

        binding.etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= Constants.SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = text.toString().trim()
                    if (query.isNotEmpty()) {
                        bookSearchViewModel.searchBooks(text.toString())
                        bookSearchViewModel.query = query
                    }
                }
            }
            startTime = endTime
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}