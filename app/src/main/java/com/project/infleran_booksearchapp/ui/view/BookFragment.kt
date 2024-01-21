package com.project.infleran_booksearchapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.project.infleran_booksearchapp.databinding.FragmentBookBinding
import com.project.infleran_booksearchapp.ui.viewmodel.BookSearchViewModel
import dagger.hilt.android.AndroidEntryPoint


// 프래그먼트에 의존성 주입 가능한 스코프 설정
@AndroidEntryPoint
class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<BookFragmentArgs>()
//    private lateinit var bookSearchViewModel: BookSearchViewModel

    private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel
        val book = args.book
        binding.webviewBook.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url)
        }
        binding.floatingBtn.setOnClickListener {
            bookSearchViewModel.saveBook(book)
            Snackbar.make(view, "Book has saved", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onPause() {
        binding.webviewBook.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.webviewBook.onResume()
    }

    companion object {

    }
}