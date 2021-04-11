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

    /** The main card`s view. */
    val card: CardView = view.findViewById(R.id.card)

    /** The plan`s topic text view. */
    val topic: TextView = view.findViewById(R.id.topic)

    /** The plan`s description text view. */
    val description: TextView = view.findViewById(R.id.description)

    /** The plan`s count trainings text view. */
    val countTrainings: TextView = view.findViewById(R.id.count)
}