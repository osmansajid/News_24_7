package com.example.news_24_7.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news_24_7.R
import com.example.news_24_7.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_main_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.apply {
            bottomBar.setupWithNavController(navController)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.newsListFragment,
                    R.id.sportsNewsListFragment,
                    R.id.entertainmentNewsListFragment,
                    R.id.searchNewsListFragment
                ),
                drawerLayout
            )
            setSupportActionBar(toolbar)
            navDrawer.setupWithNavController(navController)
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id != R.id.newsDetailsFragment) {
            ActivityCompat.finishAfterTransition(this)
        } else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}