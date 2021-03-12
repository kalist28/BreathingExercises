package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.*
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.model.training.ATraining
import ru.kalistratov.breathtraining2.model.training.SimpleTraining
import ru.kalistratov.breathtraining2.model.training.SquareTraining
import ru.kalistratov.breathtraining2.model.training.TriangleTraining
import java.lang.Exception
import java.util.*

/**
 * The class of training`s card view.
 *
 * @property view the view`s id.
 */
open class TrainingCard(val view: View) : DoubleStatusView(view) {

    /** The main card`s view. */
    public lateinit var card: CardView

    /** The training total time`s view. */
    private lateinit var time: TextView

    /** The training topic`s view. */
    private lateinit var topic: TextView

    /** The number of training inhale view. */
    private lateinit var inhale: TextView

    /** The number of training exhale view. */
    private lateinit var exhale: TextView

    /** The number of training first pause view. */
    private lateinit var firstPause: TextView

    /** The number of training second pause view. */
    private lateinit var secondPause: TextView

    /** The first pause`s image view. */
    private lateinit var firstPauseImg: ImageView

    /** The second pause`s image view. */
    private lateinit var secondPauseImg: ImageView

    /** Called when status is changing. */
    private fun init() {
         card           = view.findViewById(R.id.card)
         time           = activeView.findViewById(R.id.time)
         topic          = activeView.findViewById(R.id.topic)
         inhale         = activeView.findViewById(R.id.inhale)
         exhale         = activeView.findViewById(R.id.exhale)
         firstPause     = activeView.findViewById(R.id.first_pause)
         secondPause    = activeView.findViewById(R.id.second_pause)
         firstPauseImg  = activeView.findViewById(R.id.first_pause_img)
         secondPauseImg = activeView.findViewById(R.id.second_pause_img)
    }

    /**
     * Determine the type of workout and redirect to the desired setting.
     *
     * @param t is training.
     */
    fun setTrainingInfo(t: ATraining) {
        init()
        time.text   = t.getTime().toString()
        topic.text  = t.name
        when (t) {
            is SquareTraining -> setTrainingInfo(t)
            is SimpleTraining -> setTrainingInfo(t)
            is TriangleTraining -> setTrainingInfo(t)
            else -> throw Exception("Training type not added")
        }
    }

    /**
     * Setting up the view for simple training.
     *
     *  @param t is simple training.
     */
    private fun setTrainingInfo(t: SimpleTraining) {
        inhale.text = "${t.inhaleTime}"
        exhale.text = "${t.exhaleTime}"
        setViewGone(firstPause)
        setViewGone(secondPause)
        setViewGone(firstPauseImg)
        setViewGone(secondPauseImg)
    }

    /**
     * Setting up the view for square training.
     *
     *  @param t is square training.
     */
    private fun setTrainingInfo(t: SquareTraining) {
        inhale.text         = "${t.inhaleTime}"
        exhale.text         = "${t.exhaleTime}"
        firstPause.text     = "${t.firstPauseTime}"
        secondPause.text    = "${t.secondPauseTime}"
    }

    /**
     * Setting up the view for triangle training.
     *
     *  @param t is triangle training.
     */
    private fun setTrainingInfo(t: TriangleTraining) {
        inhale.text         = "${t.inhaleTime}"
        exhale.text         = "${t.exhaleTime}"
        firstPause.text     = "${t.pauseTime}"
        setViewGone(secondPause)
        setViewGone(secondPauseImg)
    }

    /**
     * Enabling ads if this card is ad card.
     */
    fun isAdBanner() {
        deactivateAllViews()
        val viewDeactivate: View = view.findViewById(R.id.add_banner)
        viewDeactivate.visibility = View.VISIBLE

        val mAdView : AdView = viewDeactivate.findViewById(R.id.adView)
        MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(listOf("F83FCB791B4AB587E0D0AAD7E66C34BC")).build())
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

}