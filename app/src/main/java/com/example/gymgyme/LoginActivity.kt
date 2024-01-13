package com.example.gymgyme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.app.AlertDialog
import com.example.gymgyme.utility.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            loginUser(usernameText, passwordText)
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loginUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Replace with your network call logic
                val response = performLogin(username, password)
                withContext(Dispatchers.Main) {
                    // Update UI based on response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Show error message
                }
            }
        }
    }

    private fun performLogin(username: String, password: String) {
        Thread {
            try {
                val url = URL("http://10.0.2.2:8000/auth/login")
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.setRequestProperty("Content-Type", "application/json")
                httpURLConnection.setRequestProperty("Accept", "*/*")
                httpURLConnection.doOutput = true

                val jsonInputString = JSONObject().apply {
                    put("username", username)
                    put("password", password)
                }.toString()

                httpURLConnection.outputStream.use { os ->
                    val input = jsonInputString.toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                BufferedReader(InputStreamReader(httpURLConnection.inputStream, "utf-8")).use { br ->
                    val response = StringBuilder()
                    var responseLine: String?
                    while (br.readLine().also { responseLine = it } != null) {
                        response.append(responseLine?.trim())
                    }
                    val jsonResponse = JSONObject(response.toString())
                    val jwt = jsonResponse.optString("jwt", "")
                    runOnUiThread {
                        if (jwt.isNotEmpty()) {
                            // Store the JWT for future use
                            SharedPreferencesManager.saveJWT(applicationContext, jwt)

                            // Redirect to Homepage
                            val intent = Intent(this, AppActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Show error message
                            AlertDialog.Builder(this@LoginActivity).apply {
                                setTitle("Login Failed")
                                setMessage("Invalid username or password.")
                                setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                                create()
                                show()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    // Handle the error here
                    Log.e("LoginError", "Error during login", e)
                    // Show error message or update UI accordingly
                }
            }
        }.start()
    }
}