package ru.kalistratov.breathtraining2.controller.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.holder.TrainingCard
import ru.kalistratov.breathtraining2.model.training.Training
import ru.kalistratov.breathtraining2.model.training.SimpleTraining
import ru.kalistratov.breathtraining2.view.training.TrainingActivity
import java.util.*

class TrainingSelectionAdapter(private val trainings: LinkedList<Training>,
                               val levelNum: Byte,
                               val planId: Byte,
                               val context: Context):
        RecyclerView.Adapter<TrainingCard>() {

    private var activeTraining: Int = 0
    private var adPosition: Int = 0

    init {
        activeTraining =  trainings.size / 2
        adPosition = activeTraining + 1

        /** Add ad banner. */
        if(adPosition < trainings.size)
            trainings.add(adPosition, SimpleTraining())
        else
            trainings.add(SimpleTraining())

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingCard {
        val view = LayoutInflater
                .from(parent.context).inflate(R.layout.view_card_training, parent, false)
        return TrainingCard(view)
    }

    override fun onBindViewHolder(holder: TrainingCard, position: Int) {
        holder.deactivate()
        if (position == adPosition) holder.isAdBanner()
        if (position == activeTraining) holder.activate()
        holder.setTrainingInfo( trainings[position])
        setAnimation(holder.itemView)
        holder.card.setOnClickListener {
            val intent = Intent(context, TrainingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(TrainingActivity.EXTRA_PLAN_ID, planId)
            intent.putExtra(TrainingActivity.EXTRA_LEVEL_NUMBER, levelNum)
            intent.putExtra(TrainingActivity.EXTRA_TRAINING_NUMBER, trainings[position].number)
            context.startActivity(intent)
        }

        if (position == 0) holder.view.setPadding(0,20,0,0)
        if (position == trainings.size - 1) holder.view.setPadding(0,0,0,50)
    }

    private fun setAnimation(itemView: View) {
        val duration: Long = 500
        itemView.alpha = 0f
        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()
        animator.startDelay = duration / 2
        animator.duration = duration
        animatorSet.play(animator)
        animator.start()
    }

    override fun getItemCount(): Int {
        return trainings.size
    }
}