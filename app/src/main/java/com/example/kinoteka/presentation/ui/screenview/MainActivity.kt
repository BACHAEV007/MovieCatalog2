package com.example.kinoteka.presentation.ui.screenview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kinoteka.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WelcomeScreen())
                .commit()
        }
    }
}
