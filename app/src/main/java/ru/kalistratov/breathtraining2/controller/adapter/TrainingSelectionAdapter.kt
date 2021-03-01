package ru.kalistratov.breathtraining2.controller.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.holder.TrainingCard
import ru.kalistratov.breathtraining2.model.SimpleTraining
import ru.kalistratov.breathtraining2.model.SquareTraining
import ru.kalistratov.breathtraining2.model.Training
import java.util.*

class TrainingSelectionAdapter(private val trainings: LinkedList<Training>):
        RecyclerView.Adapter<TrainingCard>() {

    private var activeTraining: Int = 0
    private var adPosition: Int = 0

    init {
        activeTraining =  trainings.size / 2
        adPosition = activeTraining + 1

        Log.e("ADDDDDDDD", "${adPosition} ${trainings.size}" )
        if(adPosition < trainings.size)
            trainings.add(adPosition, SimpleTraining())
        else
            trainings.add(SimpleTraining())

        Log.e("ADDDDDDDD", "${adPosition} ${trainings.size}" )


        for (t in trainings) {
            Log.e("TAG", "${t.name}: ${t is SquareTraining} : $t = ${t.javaClass}" )
        }
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