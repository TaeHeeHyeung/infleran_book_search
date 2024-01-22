package com.project.infleran_booksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.infleran_booksearchapp.databinding.FragmentSearchBinding
import com.project.infleran_booksearchapp.ui.adapter.BookSearchLoadStateAdapter
import com.project.infleran_booksearchapp.ui.adapter.BookSearchPagingAdapter
import com.project.infleran_booksearchapp.util.Constants
import com.project.infleran_booksearchapp.util.collectionLastStateFlow
import com.qualitybitz.booksearchapp.ui.viewmodel.SearchViewModel
import com.qualitybitz.booksearchapp.ui.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val TAG: String by lazy {
        this@SearchFragment.javaClass.name
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


//    private lateinit var bookSearchViewModel: BookSearchViewModel
//    private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()

    //    private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel
        setupRecyclerView()
        searchBooks()

//        bookSearchViewModel.searchResult.observe(viewLifecycleOwner, Observer {
//            val books = it.documents
//            bookSearchAdapter.submitList(books)
//        })

        collectionLastStateFlow(searchViewModel.searchPagingResult) {
            bookSearchAdapter.submitData(it)
        }

        binding.etSearch.setText(searchViewModel.query)
        binding.etSearch.text =
            Editable.Factory.getInstance().newEditable(searchViewModel.query)

        setupLoadState()
    }


    private fun setupRecyclerView() {
//        bookSearchAdapter = BookSearchAdapter()
        bookSearchAdapter = BookSearchPagingAdapter()

        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
//            adapter = bookSearchAdapter
            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(bookSearchAdapter::retry)
            )
        }

        bookSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBook(it)
            findNavController().navigate(action)
        }

    }

    //    private var handler: Handler = Handler(Looper.getMainLooper())
//    private var runnable: Runnable? = null
    private var job: Job? = null
    private fun searchBooks() {
        binding.etSearch.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = CoroutineScope(Dispatchers.Default).launch { delay(Constants.SEARCH_BOOKS_TIME_DELAY)
                text?.let {
                    Log.d(TAG, "text:$it");
                    val query = text.toString().trim()
                    if (query.isNotEmpty()) {
                        searchViewModel.searchBooksPaging(text.toString())
                        searchViewModel.query = query
                    }
                }
            }
        }
    }

    private fun setupLoadState() {
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = bookSearchAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptyList.isVisible = isListEmpty
            binding.rvSearchResult.isVisible = !isListEmpty
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

//            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error
//                    || loadState.append is LoadState.Error
//                    || loadState.prepend is LoadState.Error
//
//            val errorState: LoadState.Error? = loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//                ?: loadState.refresh as? LoadState.Error
//            errorState?.let {
//                Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
//            }
//
//
//            binding.btnRetry.setOnClickListener {
//                bookSearchAdapter.retry()
//            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}