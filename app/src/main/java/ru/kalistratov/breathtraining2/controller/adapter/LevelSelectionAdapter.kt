package ru.kalistratov.breathtraining2.controller.adapter

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.holder.LevelCard
import ru.kalistratov.breathtraining2.model.training.Training
import ru.kalistratov.breathtraining2.model.training.plan.PlanLevel
import java.util.*


/**
 * The adapter to display the list of levels in [RecyclerView].
 *
 * The selected item call the [OnLevelSelectListener] and passes the level object.
 *
 * @property levels is a training levels list.
 * @property context is activity context.
 */
class LevelSelectionAdapter(
    private val levels: LinkedList<out PlanLevel<out Training>>,
    private val context: Context?
) :
    RecyclerView.Adapter<LevelCard>() {

    /** The activated card on the moment. */
    private var activeItem: Int = 0

    /** The recycler view of this adapter. */
    private var recyclerView: RecyclerView? = null

    /** Levels selections listener. */
    var onLevelSelectListener: OnLevelSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelCard {
        val view = LayoutInflater
            .from(context).inflate(R.layout.view_card_level, parent, false)
        recyclerView = parent as RecyclerView
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

    /**
     * Convert dip to px.
     *
     * @param dip dp value.
     * @return dp value in pixels.
     */
    fun convert(dip: Float): Float {
        val r: Resources = context!!.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
    }

    /**
     * Activate item at [id] index.
     *
     * @param id is item index.
     */
    fun activateItem(id: Int) {
        notifyItemChanged(activeItem)
        activeItem = id
        notifyItemChanged(activeItem)
        onLevelSelectListener?.onSelect(levels[activeItem])
        var flag: Boolean
        Thread {
            do {
                flag = recyclerView?.post {
                    recyclerView?.smoothScrollToPosition(id)
                } == true
                Thread.sleep(100)
            } while (!flag)
        }.start()
    }

    /**
     * Interface definition for a callback to be invoked when a level was selected.
     */
    fun interface OnLevelSelectListener {
        /**
         * Called when a level was selected.
         *
         * @param level the selected level.
         */
        fun onSelect(level: PlanLevel<out Training>)
    }
}