package com.example.kinoteka.presentation

import android.os.Bundle
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



    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom_nav, fragment)
            .addToBackStack(null)
            .commit()
    }
}