package ru.kalistratov.breathtraining2.controller.adapter

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.holder.LevelCard
import ru.kalistratov.breathtraining2.model.Training
import ru.kalistratov.breathtraining2.model.TrainingLevel
import java.util.*

fun interface OnLevelSelectListener {
    fun onSelect(level: TrainingLevel<Training>)
}

class LevelSelectionAdapter(private val levels: LinkedList<TrainingLevel<Training>>,
                            private val context: Context?)
    : RecyclerView.Adapter<LevelCard>() {

    private var activeItem: Int = 0
    private var mRecyclerView: RecyclerView? = null
    var onLevelSelectListener: OnLevelSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelCard {
        val view = LayoutInflater
            .from(context).inflate(R.layout.view_card_level, parent, false)
        mRecyclerView = parent as RecyclerView
        return LevelCard(view, this)
    }

    override fun onBindViewHolder(holder: LevelCard, position: Int) {

        val level = levels[position]
        val levelNum = context?.getString(R.string.level) + " ${level.number}"
        holder.percent = 20
        holder.level.text = levelNum
        holder.deactivate()

        if (activeItem == position) holder.activate()
    }

    override fun getItemCount(): Int {
        return levels.size
    }

    fun convert(dip: Float): Float {
        val r: Resources = context!!.resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.displayMetrics
        )
    }

    fun activateItem(id: Int) {
        notifyItemChanged(activeItem)
        activeItem = id
        notifyItemChanged(activeItem)
        onLevelSelectListener?.onSelect(levels[activeItem])
    }
}