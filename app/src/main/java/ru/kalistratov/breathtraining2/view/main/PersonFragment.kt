package ru.kalistratov.breathtraining2.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.kalistratov.breathtraining2.R

/**
 * The fragment which load user info and progress.
 */
class PersonFragment : Fragment(R.layout.fragment_person) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topic = view.findViewById<TextView>(R.id.topic)
        topic.setTextColor(resources.getColor(R.color.person))
        topic.text = resources.getString(R.string.person)
    }
}