package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R

abstract class DoubleStatusView(view: View) : RecyclerView.ViewHolder(view) {

    /** View when status is activate. */
    private val viewActivate: View = view.findViewById(R.id.activate)

    /** View when status is deactivate. */
    private val viewDeactivate: View = view.findViewById(R.id.deactivate)

    /** Set status` view. */
    protected fun setVisibleForFrames(b: Boolean) {
        if (b) {
            viewActivate.visibility     = View.GONE
            viewDeactivate.visibility   = View.VISIBLE
        } else {
            viewActivate.visibility     = View.VISIBLE
            viewDeactivate.visibility   = View.GONE
        }
    }

    /** Set view`s deactivated status. */
    public open fun deactivate() {
        setVisibleForFrames(true)
    }

    /** Set view`s activated status. */
    public open fun activate() {
        setVisibleForFrames(false)
    }

    public open fun deactivateAllViews() {
        viewActivate.visibility     = View.GONE
        viewDeactivate.visibility   = View.GONE
    }
}