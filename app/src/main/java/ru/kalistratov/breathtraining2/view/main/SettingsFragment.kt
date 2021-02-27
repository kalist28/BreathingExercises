package ru.kalistratov.breathtraining2.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.kalistratov.breathtraining2.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topic = view.findViewById<TextView>(R.id.topic)
        topic.setTextColor(resources.getColor(R.color.settings))
        topic.text = resources.getString(R.string.settings)
    }
}