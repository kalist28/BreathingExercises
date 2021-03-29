package ru.kalistratov.breathtraining2.view.training

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.dynamicanimation.animation.DynamicAnimation
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.model.training.ATraining
import ru.kalistratov.breathtraining2.model.training.TriangleTraining
import ru.kalistratov.breathtraining2.model.training.plan.TrainingPlans
import ru.kalistratov.breathtraining2.view.training.engine.TrainingEngine
import ru.kalistratov.breathtraining2.view.training.engine.TrainingEngineService
import java.util.*

class TrainingActivity : AppCompatActivity(R.layout.activity_training)  {

    companion object {
        const val EN_PLAN_ID         = "planId"
        const val EN_LEVEL_NUMBER    = "levelNumber"
        const val EN_TRAINING_NUMBER = "trainingNumber"
    }

    private var planId: Byte = -1
    private var levelNumber: Byte = -1
    private var trainingNumber: Byte = -1
    private var training: ATraining? = null
    private lateinit var mService: TrainingEngineService
    private var mBound: Boolean? = null

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            val binder = service as TrainingEngineService.Binder
            mService = binder.service()
            mBound = true

            training?.let { mService.init(it) }
            initEngineListeners(mService.engine)
            initButtonsOnClickListeners(mService.engine)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<ImageButton>(R.id.backspace).setOnClickListener {
            onBackPressed()
        }

        planId = intent.getByteExtra(EN_PLAN_ID, -1)
        levelNumber = intent.getByteExtra(EN_LEVEL_NUMBER, -1)
        trainingNumber = intent.getByteExtra(EN_TRAINING_NUMBER, -1)

        val error = (-1).toByte()
        if (planId == error || levelNumber == error || trainingNumber == error) {
            finish()
            return
        }

        training = TrainingPlans.findTrainingInPlan(planId, levelNumber, trainingNumber)

        Log.e("TAGEEEE", "onCreate: $training " + (training is TriangleTraining) )

        val a = TrainingEngineService()
        Intent(this, a::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
        }

    }

    private fun initButtonsOnClickListeners(engine: TrainingEngine) {

        findViewById<CardView>(R.id.card_pause_and_play).setOnClickListener {
            engine.pushPauseOrPlay()
            Animations.likeAnimation(it)
        }

        findViewById<CardView>(R.id.card_stop).setOnClickListener {
            engine.stopEngine()
            finish()
        }
    }

    private fun initEngineListeners(engine: TrainingEngine) {
        engine.onPassedTimeListener = object : TrainingEngine.OnPassedTimeListener {
            override fun onPassedTime(stepTime: Int, allTime: Int) {
                findViewById<TextView>(R.id.all_time).text = allTime.toString()
                findViewById<TextView>(R.id.time).text = stepTime.toString()
            }
        }

        engine.onStepListener = object : TrainingEngine.OnStepListener {
            override fun onStep(newName: String) {
                findViewById<TextView>(R.id.topic).text = newName.toUpperCase(Locale.ROOT)
            }
        }

        engine.onPauseListener = object : TrainingEngine.OnPauseListener {
            override fun onPause() {
                findViewById<ImageView>(R.id.pause_and_play).setImageResource(R.drawable.ic_play_arrow)
            }
        }

        engine.onContinueListener = object : TrainingEngine.OnContinueListener {
            override fun onContinue() {
                findViewById<ImageView>(R.id.pause_and_play).setImageResource(R.drawable.ic_pause)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //unbindService(connection)
        //mBound = false
    }

    private object Animations {
        private val TO_NORMAL_DURATION: Long = 1000
        private val SHOW_DURATION: Long = 1000

        fun likeAnimation(view: View) {
            view.visibility = View.VISIBLE
            val animationSet = AnimationSet(false)
            animationSet.addAnimation(showAnimationSet())
            view.startAnimation(animationSet)
        }

        private fun showAnimationSet(): AnimationSet {
            val showScaleAnimation = ScaleAnimation(
                    1f, 1.2f, 1f, 1.2f,
                    Animation.RELATIVE_TO_SELF, 0.3f,
                    Animation.RELATIVE_TO_SELF, 0.3f)
            val set = AnimationSet(false)
            set.addAnimation(showScaleAnimation)
            set.duration = SHOW_DURATION
            return set
        }

        private fun toNormalAnimationSet(): AnimationSet? {
            val toNormalScaleAnimation = ScaleAnimation(
                    1.2f, 1f, 1.2f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.3f,
                    Animation.RELATIVE_TO_SELF, 0.3f)
            val set = AnimationSet(false)
            set.addAnimation(toNormalScaleAnimation)
            set.duration = TO_NORMAL_DURATION
            set.startOffset = SHOW_DURATION
            return set
        }
    }


}