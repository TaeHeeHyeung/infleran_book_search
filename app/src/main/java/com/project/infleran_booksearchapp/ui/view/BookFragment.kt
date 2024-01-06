package com.project.infleran_booksearchapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.project.infleran_booksearchapp.R
import com.project.infleran_booksearchapp.databinding.FragmentBookBinding


class BookFragment : Fragment() {

    var _binding: FragmentBookBinding? = null
    val binding get() = _binding!!
    private val args by navArgs<BookFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val book = args.book
        binding.webviewBook.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url)
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