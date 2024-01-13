package com.example.gymgyme

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gymgyme.utility.SharedPreferencesManager

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Retrieve the JWT token
        val jwtToken = SharedPreferencesManager.getJWT(applicationContext)

    }
}