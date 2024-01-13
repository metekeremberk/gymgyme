package com.example.gymgyme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymgyme.Exercise
import com.example.gymgyme.R

class ExerciseListAdapter(
    var exercises: List<Exercise>,
    private val onMoreClicked: (Exercise) -> Unit)
    : RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_exercise, parent, false)) {
        private var tvExerciseName: TextView = itemView.findViewById(R.id.tvExerciseName)
        private var btnMore: ImageButton = itemView.findViewById(R.id.btnMore)

        fun bind(exercise: Exercise, onMoreClicked: (Exercise) -> Unit) {
            tvExerciseName.text = exercise.title
            btnMore.setOnClickListener { onMoreClicked(exercise) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise, onMoreClicked)
    }

    override fun getItemCount(): Int = exercises.size
}