package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.LevelSelectionAdapter

/**
 * The class of level`s card view.
 *
 * @property view the view`s id.
 * @property adapter the adapter of recycler list.
 */
class LevelCard(private val view: View,
                private val adapter: LevelSelectionAdapter):
        DoubleStatusView(view) {

    /** View`s main view. */
    val card: CardView = view.findViewById(R.id.card)

    /** View percent in activated status. */
    val percentAct: TextView = view.findViewById(R.id.percent_act)

    /** View percent in deactivated status. */
    val percentDeact: TextView = view.findViewById(R.id.percent_deact)

    /** View`s level number. */
    val level: TextView = view.findViewById(R.id.level_number)

    /** The performance`s percent. */
    var percent: Int = 0
        set(value) {
            field = value
            val percent = "${field}%"
            percentAct.text = percent
            percentDeact.text = percent
        }

    init {
        card.setOnClickListener {
            setActive()
        }
        deactivate()
    }

    /** Listener of put this [getLayoutPosition] in adapter for activated. */
    private fun setActive() {
        adapter.activateItem(layoutPosition)
    }

    /** Set view`s deactivated status. */
    public override fun deactivate() {
        setVisibleForFrames(true)
        card.layoutParams.width = adapter.convert(125f).toInt()
        card.layoutParams.height = adapter.convert(125f).toInt()
    }

    /** Set view`s activated status.  */
    public override fun activate() {
        setVisibleForFrames(false)
        card.layoutParams.width = adapter.convert(140f).toInt()
        card.layoutParams.height = adapter.convert(140f).toInt()
    }

}