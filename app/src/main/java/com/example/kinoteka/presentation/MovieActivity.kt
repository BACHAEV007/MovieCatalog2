package com.example.kinoteka.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kinoteka.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MovieActivity : AppCompatActivity(R.layout.bottom_navigation) {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationView = findViewById(R.id.bottom_Navigation_view)
        bottomNavigationView.itemIconTintList = null
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom_nav, FeedScreen())
            .addToBackStack(null)
            .commit()
        setupBottomNavigation()
        supportFragmentManager.addOnBackStackChangedListener {
            syncBottomNavigationWithFragment()
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_feed -> {
                    replaceFragment(FeedScreen())
                    true
                }
                R.id.menu_movies -> {
                    replaceFragment(MoviesScreen())
                    true
                }
                R.id.menu_favorites -> {
                    replaceFragment(FavoritesScreenFragment())
                    true
                }
                R.id.menu_profile -> {
                    replaceFragment(ProfileScreen())
                    true
                }
                else -> false
            }
        }
    }

    fun hideBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }

    fun updateSelectedBottomNavigationItem(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
    }

    private fun syncBottomNavigationWithFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_bottom_nav)
        val itemId = when (currentFragment) {
            is FeedScreen -> R.id.menu_feed
            is MoviesScreen -> R.id.menu_movies
            is FavoritesScreenFragment -> R.id.menu_favorites
            is ProfileScreen -> R.id.menu_profile
            else -> return
        }
        bottomNavigationView.menu.findItem(itemId).isChecked = true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom_nav, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}