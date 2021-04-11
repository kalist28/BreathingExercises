package ru.kalistratov.breathtraining2.view.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.PlanSelectionAdapter


/**
 * The fragment which load list of training plans to choose.
 */
class PlanSelectionFragment : Fragment(R.layout.fragment_selection_training) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.topic).setTextColor(resources.getColor(R.color.home))
        val topic = view.findViewById<TextView>(R.id.topic)
        topic.setTextColor(resources.getColor(R.color.home))
        topic.text = resources.getString(R.string.home)

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = context?.let { PlanSelectionAdapter(it) }
    }
}