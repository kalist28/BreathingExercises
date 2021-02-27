package ru.kalistratov.breathtraining2.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.model.TrainingPlan
import ru.kalistratov.breathtraining2.model.TrainingPlans

class DetailsFragment : Fragment(R.layout.fragment_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topic = view.findViewById<TextView>(R.id.topic)
        topic.setTextColor(resources.getColor(R.color.details))
        topic.text = resources.getString(R.string.details)
    }
}