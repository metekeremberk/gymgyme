package com.example.gymgyme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import android.app.AlertDialog
import android.util.Log

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val newUsername = findViewById<EditText>(R.id.new_username)
        val newPassword = findViewById<EditText>(R.id.new_password)
        val signup = findViewById<Button>(R.id.signup)
        val loginButton = findViewById<Button>(R.id.loginButton)

        signup.setOnClickListener {
            val newUsernameText = newUsername.text.toString()
            val newPasswordText = newPassword.text.toString()

            Thread {
                try {
                    val url = URL("http://10.0.2.2:8000/auth/register") // Use 10.0.2.2 for emulator to access localhost
                    val httpURLConnection = url.openConnection() as HttpURLConnection
                    httpURLConnection.requestMethod = "POST"
                    httpURLConnection.setRequestProperty("Content-Type", "application/json")
                    httpURLConnection.setRequestProperty("Accept", "*/*")
                    httpURLConnection.doOutput = true

                    val jsonInputString = "{\"username\": \"$newUsernameText\", \"password\": \"$newPasswordText\"}"
                    httpURLConnection.outputStream.use { os ->
                        val input = jsonInputString.toByteArray(Charsets.UTF_8)
                        os.write(input, 0, input.size)
                    }

                    // Read response
                    BufferedReader(InputStreamReader(httpURLConnection.inputStream, "utf-8")).use { br ->
                        val response = StringBuilder()
                        var responseLine: String?
                        while (br.readLine().also { responseLine = it } != null) {
                            response.append(responseLine?.trim())
                        }
                        runOnUiThread {
                            if (response.toString().isNotEmpty()) {
                                Log.d("SignupSuccess", response.toString())
                            } else {
                                Log.e("SignupFailed", "No response from server. Please try again later.")
                                AlertDialog.Builder(this@SignupActivity).apply {
                                    setTitle("Signup Failed")
                                    setMessage("No response from server. Please try again later.")
                                    setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SignupError", "Error during signup", e)
                    e.printStackTrace()
                }
            }.start()
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}