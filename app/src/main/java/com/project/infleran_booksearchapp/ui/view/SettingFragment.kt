package com.project.infleran_booksearchapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.project.infleran_booksearchapp.R
import com.project.infleran_booksearchapp.databinding.FragmentSettingBinding
import com.project.infleran_booksearchapp.util.Sort
import com.qualitybitz.booksearchapp.ui.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    //    private lateinit var bookSearchViewModel: BookSearchViewModel
//    private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val settingViewModel by activityViewModels<SettingViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        saveSettings()

        loadSettings()

        showWorkStatus()
    }


    private fun saveSettings() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value: String = when (checkedId) {
                R.id.rb_accurate -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            settingViewModel.saveSortMode(value)
        }
        binding.swCacheDelete.setOnCheckedChangeListener() { _, isChecked ->
            settingViewModel.saveCacheDeleteMode(isChecked)
            if (isChecked) {
                settingViewModel.setWork()
            } else {
                settingViewModel.deleteWork()
            }
        }

    }

    private fun loadSettings() {
        lifecycleScope.launch {
            val buttonId = when (settingViewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accurate
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }

        lifecycleScope.launch {
            val mode = settingViewModel.getCacheDeleteMode()
            binding.swCacheDelete.isChecked = mode
        }
    }

    private fun showWorkStatus() {
        settingViewModel.getWorkStatus().observe(viewLifecycleOwner) { workInfo ->
            Log.d("WorkManager", workInfo.toString())
            if (workInfo.isEmpty()) {
                binding.tvWorkStatus.text = "No works"
            } else {
                binding.tvWorkStatus.text = workInfo[0].state.toString()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}