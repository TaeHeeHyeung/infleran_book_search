package com.project.infleran_booksearchapp.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.infleran_booksearchapp.databinding.FragmentSearchBinding
import com.project.infleran_booksearchapp.ui.adapter.BookSearchAdapter
import com.project.infleran_booksearchapp.ui.viewmodel.BookSearchViewModel
import com.project.infleran_booksearchapp.util.Constants


class SearchFragment : Fragment() {

    private val TAG: String by lazy {
        this@SearchFragment.javaClass.name
    }

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
        bookSearchAdapter.setOnItemClickListener {

            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBook(it)
            findNavController().navigate(action)
        }
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookSearchAdapter
        }
    }

    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private fun searchBooks() {
        binding.etSearch.addTextChangedListener { text: Editable? ->
            if (runnable != null) {
                handler.removeCallbacks(runnable!!)
            }
            // 텍스트 입력 후 N 초 간 입력 없으면 검색 실행
            runnable = Runnable {
                text?.let {
                    Log.d(TAG, "text:$it");
                    val query = text.toString().trim()
                    if (query.isNotEmpty()) {
                        bookSearchViewModel.searchBooks(text.toString())
                        bookSearchViewModel.query = query
                    }
                }
            }
            handler.postDelayed(runnable!!, Constants.SEARCH_BOOKS_TIME_DELAY)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}