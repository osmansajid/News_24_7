package com.example.news_24_7.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
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
            initIcons()
            setIconOnCreate()
            navDrawer.menu.findItem(R.id.countryUSA).setOnMenuItemClickListener {
                changeIcon(R.id.countryUSA)
                saveCountryInSP("us","en")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }
            navDrawer.menu.findItem(R.id.countryUK).setOnMenuItemClickListener {
                changeIcon(R.id.countryUK)
                saveCountryInSP("gb","en")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }
            navDrawer.menu.findItem(R.id.countryFrance).setOnMenuItemClickListener {
                changeIcon(R.id.countryFrance)
                saveCountryInSP("fr","fr")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }
            navDrawer.menu.findItem(R.id.countryIndia).setOnMenuItemClickListener {
                changeIcon(R.id.countryIndia)
                saveCountryInSP("in","hi")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }
            navDrawer.menu.findItem(R.id.countryChina).setOnMenuItemClickListener {
                changeIcon(R.id.countryChina)
                saveCountryInSP("cn","zh")
                drawerLayout.closeDrawers()
                refreshPage()
                true
            }

        }

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initIcons(){
        binding.navDrawer.menu.findItem(R.id.countryUSA).setActionView(R.layout.menu_image)
        binding.navDrawer.menu.findItem(R.id.countryUK).setActionView(R.layout.menu_image)
        binding.navDrawer.menu.findItem(R.id.countryFrance).setActionView(R.layout.menu_image)
        binding.navDrawer.menu.findItem(R.id.countryIndia).setActionView(R.layout.menu_image)
        binding.navDrawer.menu.findItem(R.id.countryChina).setActionView(R.layout.menu_image)
        binding.navDrawer.menu.findItem(R.id.countryUSA).actionView.visibility = View.INVISIBLE
        binding.navDrawer.menu.findItem(R.id.countryUK).actionView.visibility = View.INVISIBLE
        binding.navDrawer.menu.findItem(R.id.countryFrance).actionView.visibility = View.INVISIBLE
        binding.navDrawer.menu.findItem(R.id.countryIndia).actionView.visibility = View.INVISIBLE
        binding.navDrawer.menu.findItem(R.id.countryChina).actionView.visibility = View.INVISIBLE
    }

    private fun setIconOnCreate(){
        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        when (sharedPreferences.getString(Constants.COUNTRY_CODE,"us")) {
            "us" -> binding.navDrawer.menu.findItem(R.id.countryUSA).actionView.visibility = View.VISIBLE
            "gb" -> binding.navDrawer.menu.findItem(R.id.countryUK).actionView.visibility = View.VISIBLE
            "fr" -> binding.navDrawer.menu.findItem(R.id.countryFrance).actionView.visibility = View.VISIBLE
            "in" -> binding.navDrawer.menu.findItem(R.id.countryIndia).actionView.visibility = View.VISIBLE
            "cn" -> binding.navDrawer.menu.findItem(R.id.countryChina).actionView.visibility = View.VISIBLE
        }
    }

    private fun changeIcon(resourceId : Int){
        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        when (sharedPreferences.getString(Constants.COUNTRY_CODE,"us")) {
            "us" -> binding.navDrawer.menu.findItem(R.id.countryUSA).actionView.visibility = View.INVISIBLE
            "gb" -> binding.navDrawer.menu.findItem(R.id.countryUK).actionView.visibility = View.INVISIBLE
            "fr" -> binding.navDrawer.menu.findItem(R.id.countryFrance).actionView.visibility = View.INVISIBLE
            "in" -> binding.navDrawer.menu.findItem(R.id.countryIndia).actionView.visibility = View.INVISIBLE
            "cn" -> binding.navDrawer.menu.findItem(R.id.countryChina).actionView.visibility = View.INVISIBLE
        }
        binding.navDrawer.menu.findItem(resourceId).actionView.visibility = View.VISIBLE
    }

    private fun saveCountryInSP(countryCode: String,languageCode: String) {
        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.COUNTRY_CODE, countryCode)
        editor.putString(Constants.LANGUAGE_CODE, languageCode)
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