package com.example.gymgyme
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val newUsername = findViewById<EditText>(R.id.new_username)
        val newPassword = findViewById<EditText>(R.id.new_password)
        val signup = findViewById<Button>(R.id.signup)
        val loginButton = findViewById<Button>(R.id.loginButton)

        signup.setOnClickListener {
            // Add your signup logic here
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}