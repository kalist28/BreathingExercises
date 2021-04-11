package ru.kalistratov.breathtraining2.controller.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.holder.TrainingPlanCard
import ru.kalistratov.breathtraining2.model.training.plan.TrainingPlans
import ru.kalistratov.breathtraining2.view.training.TrainingSelectionActivity

/**
 * The adapter to display the list of training`s plans in [RecyclerView].
 *
 * The selected item starts the [TrainingSelectionActivity] and passes the plan ID.
 *
 * @param context the context of activity.
 */
class PlanSelectionAdapter(private val context: Context)
    : RecyclerView.Adapter<TrainingPlanCard>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingPlanCard {
        val view = LayoutInflater
            .from(context).inflate(R.layout.view_card_plan, parent, false)
        return TrainingPlanCard(view)
    }

    override fun onBindViewHolder(holder: TrainingPlanCard, position: Int) {
        val plan = TrainingPlans.plans[position]
        val planId = context.getString(R.string.plan_id)
        holder.topic.text = plan.name
        holder.description.text = plan.description
        holder.countTrainings.text = "${plan.levelsCount()}"
        holder.card.setOnClickListener {
            val intent = Intent(context, TrainingSelectionActivity::class.java)
            intent.putExtra(planId, position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return TrainingPlans.plans.size
    }
}