package com.project.infleran_booksearchapp.ui.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.WorkManager
import com.project.infleran_booksearchapp.R
import com.project.infleran_booksearchapp.data.db.BookSearchDatabase
import com.project.infleran_booksearchapp.data.repository.BookSearchRepositoryImpl
import com.project.infleran_booksearchapp.databinding.ActivityMainBinding
import com.project.infleran_booksearchapp.ui.viewmodel.BookSearchViewModel
import com.project.infleran_booksearchapp.ui.viewmodel.BookSearchViewModelProviderFactory
import com.project.infleran_booksearchapp.util.Constants.DATASTORE_NAME

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var bookSearchViewModel: BookSearchViewModel

    private lateinit var navController: NavController

    lateinit var appBarConfiguration: AppBarConfiguration

    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

    private val workManager = WorkManager.getInstance(application)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        setupBottomNavigationView()
//        if (savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemId = R.id.search_fragment
//        }
        setupJetPackNavigation()

        val database = BookSearchDatabase.getInstance(this)
        val bookSearchRepository = BookSearchRepositoryImpl(database, dataStore)
        val factory = BookSearchViewModelProviderFactory(bookSearchRepository, workManager, this)
        bookSearchViewModel = ViewModelProvider(this, factory)[BookSearchViewModel::class.java]


    }

    private fun setupJetPackNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.book_search_nav_host_fragment)
                as NavHostFragment? ?: return
        navController = host.navController

        // 바텀 네비게이션에 연결
        binding.bottomNavigationView.setupWithNavController(navController)

        // 앱바 네비게이션 연결
        appBarConfiguration = AppBarConfiguration(
            // 뒤로가기시 탑 레벨로 이동
            //navController.graph

            // 각 프래그먼트들을 탑 레벨로 설정
            setOf(
                R.id.fragment_search,
                R.id.fragment_favorite,
                R.id.fragment_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
//    private fun setupBottomNavigationView() {
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.search_fragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SearchFragment())
//                        .commit()
//                    true
//                }
//
//                R.id.fragment_favorite -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, FavoriteFragment())
//                        .commit()
//                    true
//                }
//
//                R.id.fragment_setting -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SettingFragment())
//                        .commit()
//                    true
//                }
//
//                else -> false
//            }
//        }
//    }
}