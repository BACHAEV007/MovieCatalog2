package com.example.kinoteka.presentation.ui.screenview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kinoteka.R
import com.example.kinoteka.data.datasource.TokenDataSource

class LaunchScreen : AppCompatActivity(R.layout.launch_screen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenDataSource = TokenDataSource(this)
        val token = tokenDataSource.getToken()

        val nextActivityClass = if (token.isNullOrEmpty()) {
            MainActivity::class.java
        } else {
            MovieActivity::class.java
        }

        val intent = Intent(this, nextActivityClass)
        startActivity(intent)
        finish()
    }
}