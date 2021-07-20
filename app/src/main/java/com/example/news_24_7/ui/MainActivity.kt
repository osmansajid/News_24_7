package com.example.news_24_7.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news_24_7.R
import com.example.news_24_7.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_main_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.newsListFragment,
            R.id.sportsNewsListFragment,
            R.id.entertainmentNewsListFragment,
            R.id.searchNewsListFragment
        ))
        setupActionBarWithNavController(navController,appBarConfiguration)

        //this doesn't work for some reason
        /*binding.apply {
           bottomBar.setupWithNavController(navController)
        }*/

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_bar)
        bottomBar.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        if(navController.currentDestination?.id   !=  R.id.newsDetailsFragment){
            ActivityCompat.finishAfterTransition(this)
        }
        else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}