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

/**
 * The adapter to display the list of trainings in [RecyclerView].
 *
 * The selected item starts the [TrainingActivity] and passes the plan ID,
 * level number and , training number.
 *
 * @param trainings the trainings list of level.
 * @param levelNum the level id in which will select training.
 * @param planId the plan id in which will select training.
 * @param context the context of activity.
 */
class TrainingSelectionAdapter(
    private val trainings: LinkedList<Training>,
    private val levelNum: Byte,
    private val planId: Byte,
    val context: Context
) :
    RecyclerView.Adapter<TrainingCard>() {

    /**
     * The active training.
     * Recommended for the user according to his progress.
     */
    var activeTraining: Int = -1
        set(value) {
            field = value - 1
            adPosition = activeTraining
        }

    /**
     * The ad card view.
     * It`s stand under [activeTraining], or if active training negative stand under first.
     */
    private var adPosition: Int = 0
        set(value) {
            field = value + 1
            if (field <= 0) return
            if (adPosition < trainings.size)
                trainings.add(adPosition, SimpleTraining())
            else
                trainings.add(SimpleTraining())
        }

    init {
        adPosition = 0
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
        holder.setTrainingInfo(trainings[position])
        setAnimation(holder.itemView)
        holder.card.setOnClickListener { startTrainingActivity(position) }

        if (position == 0) holder.view.setPadding(0, 20, 0, 0)
        if (position == trainings.size - 1) holder.view.setPadding(0, 0, 0, 50)
    }

    override fun getItemCount(): Int {
        return trainings.size
    }

    /**
     * Start training activity and send:
     *
     * [planId] - plan id in which select training,
     * [levelNum] - level number in which select training,
     * [position] - training number.
     *
     * @param position the position of selected training.
     */
    private fun startTrainingActivity(position: Int) {
        val intent = Intent(context, TrainingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(TrainingActivity.EXTRA_PLAN_ID, planId)
        intent.putExtra(TrainingActivity.EXTRA_LEVEL_NUMBER, levelNum)
        intent.putExtra(TrainingActivity.EXTRA_TRAINING_NUMBER, trainings[position].number)
        context.startActivity(intent)
    }

    /**
     * Description animation for views.
     *
     * @param itemView the view to which need to show animation.
     */
    private fun setAnimation(itemView: View) {
        val duration: Long = 500
        val propName = "alpha"
        itemView.alpha = 0f
        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, propName, 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, propName, 0f).start()
        animator.startDelay = duration / 2
        animator.duration = duration
        animatorSet.play(animator)
        animator.start()
    }


}