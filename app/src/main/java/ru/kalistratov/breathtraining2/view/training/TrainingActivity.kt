package ru.kalistratov.breathtraining2.view.training

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.VoiceManager
import ru.kalistratov.breathtraining2.model.training.Training
import ru.kalistratov.breathtraining2.model.training.plan.TrainingPlans
import ru.kalistratov.breathtraining2.view.training.engine.TrainingEngine
import ru.kalistratov.breathtraining2.view.training.engine.TrainingEngineService
import java.util.*

/**
 * Training activity class.
 * Starts the training engine of a specific training plan
 * and provides interaction and visual training results.
 */
class TrainingActivity : AppCompatActivity(R.layout.activity_training) {

    companion object {
        private const val ERROR : Byte = -1
        const val EXTRA_PLAN_ID = "planId"
        const val EXTRA_LEVEL_NUMBER = "levelNumber"
        const val EXTRA_TRAINING_NUMBER = "trainingNumber"
    }

    /** Engine service. */
    private lateinit var service: TrainingEngineService

    /** Engine service` intent. */
    private lateinit var serviceIntent: Intent

    /** User selected training. */
    private  var training: Training? = null

    /**
     * Prevents accidental closing of an activity.
     * It`s true when user pressed once.
     * But if user will not repeat back pressed during five second, var sets false.
     */
    private var exitFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<ImageButton>(R.id.backspace).setOnClickListener {
            onBackPressed()
        }

        val planId = intent.getByteExtra(EXTRA_PLAN_ID, ERROR)
        val levelNumber = intent.getByteExtra(EXTRA_LEVEL_NUMBER, ERROR)
        val trainingNumber = intent.getByteExtra(EXTRA_TRAINING_NUMBER, ERROR)

        if (planId == ERROR || levelNumber == ERROR || trainingNumber == ERROR) {
            finish()
            return
        }
        training = TrainingPlans.findTrainingInPlan(planId, levelNumber, trainingNumber)
        setStartInfo()

        val mAdView : AdView = findViewById(R.id.adView)
        MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(listOf("F83FCB791B4AB587E0D0AAD7E66C34BC")).build())
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        Intent(this, TrainingEngineService()::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
            serviceIntent = intent
        }

    }

    override fun onStop() {
        stopService(serviceIntent)
        super.onStop()
    }

    override fun onBackPressed() {
        if (exitFlag) {
            super.onBackPressed()
        } else {
            Toast.makeText(baseContext, "Нажмите еще раз для выхода", Toast.LENGTH_LONG).show()
        }
        exitFlag = !exitFlag
        Thread {
            Thread.sleep(5000)
            exitFlag = false
        }.start()
    }

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder = iBinder as TrainingEngineService.Binder
            service = binder.service()
            training?.let { service.init(it) }
            connectServiceWithViews()

            startCountdown()
        }
        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    /**
     * Countdown to start workout.
     */
    private fun startCountdown() {
        Thread {
            VoiceManager.playVoice(baseContext, VoiceManager.Voice.TRAINING_START)
            Thread.sleep(1500)
            for (sec in 3 downTo 1) {
                Thread.sleep(1000)
                val soundId = VoiceManager.getNumbersVoice(sec)
                VoiceManager.playVoice(baseContext, soundId)
            }
            Thread.sleep(1000)
            service.startEngine()
        }.start()
    }

    /** Set start params for views. */
    private fun setStartInfo() {
        findViewById<TextView>(R.id.topic).text = training?.getStepInfo(0)?.stepName
        findViewById<TextView>(R.id.time).text = training?.getStepTime(0).toString()
    }

    /** Connect the service with views. */
    private fun connectServiceWithViews() {
        initEngineListeners()
        initButtonsOnClickListeners()
    }

    /** Initialize views click actions. */
    private fun initButtonsOnClickListeners() {

        findViewById<CardView>(R.id.card_pause_and_play).setOnClickListener {
            service.pushPauseOrContinue()
        }

        findViewById<CardView>(R.id.card_stop).setOnClickListener {
            service.stopEngine()
        }

        findViewById<CardView>(R.id.card_on_and_off).setOnClickListener {
            val idDrawable =
                if (service.pushOnOrOffVibrator()) R.drawable.ic_vibration_off
                else R.drawable.ic_vibration_on
            findViewById<ImageView>(R.id.img_vibration).setImageResource(idDrawable)
        }
    }

    /** Initialize engine listeners. */
    private fun initEngineListeners() {
        service.onPassedTimeListener = object : TrainingEngine.OnPassedTimeListener {
            override fun onPassedTime(stepTime: Int, formatTime: String) {
                findViewById<TextView>(R.id.all_time).text = formatTime
                findViewById<TextView>(R.id.time).text = stepTime.toString()
            }

        }

        service.onStopEngineListener = object : TrainingEngine.OnStopEngineListener {
            override fun onStopEngine() {
                finish()
            }
        }

        service.onStepListener = object : TrainingEngine.OnStepListener {
            override fun onStep(newName: String) {
                findViewById<TextView>(R.id.topic).text = newName.toUpperCase(Locale.ROOT)
            }
        }

        service.onPauseListener = object : TrainingEngine.OnPauseListener {
            override fun onPause() {
                findViewById<ImageView>(R.id.pause_and_play).setImageResource(R.drawable.ic_play_arrow)
            }
        }

        service.onContinueListener = object : TrainingEngine.OnContinueListener {
            override fun onContinue() {
                findViewById<ImageView>(R.id.pause_and_play).setImageResource(R.drawable.ic_pause)
            }
        }
    }



}