package ru.kalistratov.breathtraining2.controller.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.holder.TrainingPlanCard
import ru.kalistratov.breathtraining2.model.TrainingPlans
import ru.kalistratov.breathtraining2.view.training.TrainingSelectionActivity

class PlanSelectionAdapter(private val context: Context?)
    : RecyclerView.Adapter<TrainingPlanCard>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingPlanCard {
        val view = LayoutInflater
            .from(context).inflate(R.layout.view_card_plan, parent, false)
        return TrainingPlanCard(view)
    }

    override fun onBindViewHolder(holder: TrainingPlanCard, position: Int) {
        val plan = TrainingPlans.plans[position]
        holder.topic.text = plan.name
        holder.description.text = plan.description
        holder.countTrainings.text = "${plan.levelsCount()}"
        holder.card.setOnClickListener {
            val intent = Intent(context, TrainingSelectionActivity::class.java)
            intent.putExtra("planId", position)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return TrainingPlans.plans.size
    }
}