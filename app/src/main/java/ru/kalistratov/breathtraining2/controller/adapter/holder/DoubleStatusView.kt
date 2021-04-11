package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kalistratov.breathtraining2.R

/**
 * View holder with two status.
 *
 * The view defines two fragments for the active state [viewActivate] with the ID "activate"
 * and the inactive state [viewDeactivate] with the ID "deactivate".
 *
 * @param view is card layout.
 */
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
    open fun deactivate() {
        setVisibleForFrames(true)
        activeView = viewDeactivate
    }

    /** Set view`s activated status. */
    open fun activate() {
        setVisibleForFrames(false)
        activeView = viewActivate
    }

    /** Deactivate all views. */
    open fun deactivateAllViews() {
        setViewGone(viewActivate)
        setViewGone(viewDeactivate)
    }

    /**
     * Set [view] visibility status is gone.
     *
     * @param view is element view.
     */
    protected fun setViewGone(view: View) {
        view.visibility = View.GONE
    }

    /**
     * Set [view] visibility status is visible.
     *
     * @param view is element view.
     */
    protected fun setViewVisible(view: View) {
        view.visibility = View.VISIBLE
    }
}