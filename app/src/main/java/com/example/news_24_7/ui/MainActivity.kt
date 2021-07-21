package com.example.news_24_7.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.news_24_7.NavGraphDirections
import com.example.news_24_7.R
import com.example.news_24_7.constants.Constants
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
            navDrawer.itemIconTintList = null
            navDrawer.menu.findItem(R.id.countryUSA).setOnMenuItemClickListener {
                saveCountryInSP("us")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }
            navDrawer.menu.findItem(R.id.countryUK).setOnMenuItemClickListener {
                saveCountryInSP("gb")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }
            navDrawer.menu.findItem(R.id.countryFrance).setOnMenuItemClickListener {
                saveCountryInSP("fr")
                drawerLayout.closeDrawers()
                refreshPage()
               // findNavController(this@).navigate(R.id.newsDetailsFragment)
                true
            }
            navDrawer.menu.findItem(R.id.countryIndia).setOnMenuItemClickListener {
                saveCountryInSP("in")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }
            navDrawer.menu.findItem(R.id.countryChina).setOnMenuItemClickListener {
                saveCountryInSP("cn")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }

        }

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun saveCountryInSP(countryCode: String) {
        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.COUNTRY_CODE, countryCode)
        Log.d("MainActivity", "saveCountryInSP: $countryCode")
        editor.apply()

    }

    private fun refreshPage(){
        val action = NavGraphDirections.actionGlobalNewsListFragment()
        navController.navigate(action)
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