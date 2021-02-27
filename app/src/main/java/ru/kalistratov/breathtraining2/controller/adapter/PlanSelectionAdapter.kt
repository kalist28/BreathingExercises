package ru.kalistratov.breathtraining2.controller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.holder.TrainingPlanCard
import ru.kalistratov.breathtraining2.model.SimpleBreathPlan
import ru.kalistratov.breathtraining2.model.TrainingPlans
import ru.kalistratov.breathtraining2.view.training.TrainingSelectionActivity

class PlanSelectionAdapter(val context: Context?)
    : RecyclerView.Adapter<TrainingPlanCard>() {


    init {
        for (f in TrainingPlans.plans) {
            Log.e("TAG2", "${f.name}: ${f is SimpleBreathPlan}" )
        }
    }

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
            intent.putExtra("trainingId", position)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return TrainingPlans.plans.size
    }
}