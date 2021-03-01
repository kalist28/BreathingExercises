package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R

abstract class DoubleStatusView(view: View) : RecyclerView.ViewHolder(view) {

    /** View when status is activate. */
    private val viewActivate: View = view.findViewById(R.id.activate)

    /** View when status is deactivate. */
    private val viewDeactivate: View = view.findViewById(R.id.deactivate)

    /** The active view on the moment. */
    protected var activeView : View = view

    /** Set status` view. */
    protected fun setVisibleForFrames(b: Boolean) {
        if (b) {
            setViewGone(viewActivate)
            setViewVisible(viewDeactivate)
        } else {
            setViewVisible(viewActivate)
            setViewGone(viewDeactivate)
        }
    }

    /** Set view`s deactivated status. */
    public open fun deactivate() {
        setVisibleForFrames(true)
        activeView = viewDeactivate
    }

    /** Set view`s activated status. */
    public open fun activate() {
        setVisibleForFrames(false)
        activeView = viewActivate
    }

    public open fun deactivateAllViews() {
        setViewGone(viewActivate)
        setViewGone(viewDeactivate)
    }

    protected fun setViewGone(v: View) {
        v.visibility = View.GONE
    }

    protected fun setViewVisible(v: View) {
        v.visibility = View.VISIBLE
    }
}