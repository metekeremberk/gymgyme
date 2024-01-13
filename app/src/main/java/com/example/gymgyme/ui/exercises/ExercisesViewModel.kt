package com.example.gymgyme.ui.exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gymgyme.Exercise
import com.example.gymgyme.utility.SharedPreferencesManager
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {

    val exercises = MutableLiveData<List<Exercise>>()

    init {
        fetchExercises()
    }

    private fun fetchExercises() {
        Thread {
            try {
                val url = URL("http://10.0.2.2:8000/exercise/")
                val urlConnection = url.openConnection() as HttpURLConnection
                val jwtToken = SharedPreferencesManager.getJWT(getApplication<Application>().applicationContext)

                urlConnection.apply {
                    requestMethod = "GET"
                    setRequestProperty("Authorization", "Bearer $jwtToken")
                    inputStream.bufferedReader().use {
                        val response = it.readText()
                        val jsonArray = JSONArray(response)
                        val exercisesList = mutableListOf<Exercise>()

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val exercise = Exercise(
                                jsonObject.optString("title"),
                                jsonArrayToList(jsonObject.optJSONArray("primary")),
                                jsonArrayToList(jsonObject.optJSONArray("secondary")),
                                jsonArrayToList(jsonObject.optJSONArray("howTo"))
                            )
                            exercisesList.add(exercise)
                        }

                        exercises.postValue(exercisesList)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the error appropriately
            }
        }.start()
    }

    private fun jsonArrayToList(jsonArray: JSONArray?): List<String> {
        if (jsonArray == null) return emptyList()
        return List(jsonArray.length()) { i -> jsonArray.optString(i) }
    }
}
