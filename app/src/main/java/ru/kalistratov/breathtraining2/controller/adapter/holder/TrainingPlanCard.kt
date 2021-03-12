package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R

/**
 * The class of training`s plan card view.
 *
 * @param view the view`s id.
 */
class TrainingPlanCard(view: View) : RecyclerView.ViewHolder(view) {
    val card: CardView              = view.findViewById(R.id.card)
    val topic: TextView             = view.findViewById(R.id.topic)
    val description: TextView       = view.findViewById(R.id.description)
    val countTrainings: TextView    = view.findViewById(R.id.count)
}