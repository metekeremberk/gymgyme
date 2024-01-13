package com.example.gymgyme.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymgyme.R
import com.example.gymgyme.adapter.ExerciseListAdapter

class ExercisesFragment : Fragment() {

    private val viewModel: ExercisesViewModel by viewModels()
    private lateinit var exerciseAdapter: ExerciseListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize with an empty adapter
        exerciseAdapter = ExerciseListAdapter(emptyList()) { exercise ->
            // Handle 'More' button click
        }
        val rvExercises = view.findViewById<RecyclerView>(R.id.rvExercises)
        rvExercises.layoutManager = LinearLayoutManager(context)
        rvExercises.adapter = exerciseAdapter

        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            // Update adapter's data
            exerciseAdapter.exercises = exercises
            exerciseAdapter.notifyDataSetChanged()
        }
    }
}
