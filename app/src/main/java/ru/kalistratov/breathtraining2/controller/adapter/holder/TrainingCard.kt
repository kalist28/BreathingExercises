package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.*
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.model.SimpleTraining
import ru.kalistratov.breathtraining2.model.SquareTraining
import ru.kalistratov.breathtraining2.model.Training
import java.lang.Exception
import java.util.*

open class TrainingCard(val view: View) : DoubleStatusView(view) {

    private lateinit var card: CardView
    private lateinit var time: TextView
    private lateinit var topic: TextView
    private lateinit var inhale: TextView
    private lateinit var exhale: TextView
    private lateinit var firstPause: TextView
    private lateinit var secondPause: TextView
    private lateinit var firstPauseImg: ImageView
    private lateinit var secondPauseImg: ImageView


    /** Called when status is changing. */
    fun init() {
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

    fun setTrainingInfo(t: Training) {
        init()
        Log.e("TAG", "setTrainingInfo: + $t", )
        time.text   = t.getTime().toString()
        topic.text  = t.name
        when (t) {
            is SquareTraining -> setTrainingInfo(t)
            is SimpleTraining -> setTrainingInfo(t)
            else -> throw Exception("Training type not added")
        }
    }

    private fun setTrainingInfo(t: SimpleTraining) {
        inhale.text = "${t.inhaleTime}"
        exhale.text = "${t.exhaleTime}"
        setViewGone(firstPause)
        setViewGone(secondPause)
        setViewGone(firstPauseImg)
        setViewGone(secondPauseImg)
    }

    private fun setTrainingInfo(t: SquareTraining) {
        inhale.text         = "${t.inhaleTime}"
        exhale.text         = "${t.exhaleTime}"
        firstPause.text     = "${t.exhaleTime}"
        secondPause.text    = "${t.exhaleTime}"
    }

    fun isAdBanner() {
        deactivateAllViews()
        val viewDeactivate: View = view.findViewById(R.id.add_banner)
        viewDeactivate.visibility = View.VISIBLE

        val mAdView : AdView = viewDeactivate.findViewById(R.id.adView)
        MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("F83FCB791B4AB587E0D0AAD7E66C34BC")).build())
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

}