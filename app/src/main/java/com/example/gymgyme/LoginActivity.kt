package com.example.gymgyme
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val signupButton = findViewById<Button>(R.id.signupButton)

        login.setOnClickListener {
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()

            Thread {
                // Perform network operation for login
                // Use HttpUrlConnection or any other network library like Retrofit, OkHttp
                // Example URL: "http://localhost:8000/auth/login"
                // Don't forget to handle network exceptions and responses appropriately
            }.start()
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}