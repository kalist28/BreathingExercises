package ru.kalistratov.breathtraining2.view.training

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
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
import ru.kalistratov.breathtraining2.controller.adapter.TrainingVoiceManager
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
        const val EN_PLAN_ID = "planId"
        const val EN_LEVEL_NUMBER = "levelNumber"
        const val EN_TRAINING_NUMBER = "trainingNumber"
    }

    private lateinit var service: TrainingEngineService

    private var exitFlag: Boolean = false
    private var planId: Byte = ERROR
    private var levelNumber: Byte = ERROR
    private var trainingNumber: Byte = ERROR
    private var training: Training? = null
    private var serviceIntent: Intent ? = null

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder = iBinder as TrainingEngineService.Binder
            service = binder.service()
            training?.let { service.init(it) }
            connectService(service)

            Thread {
                TrainingVoiceManager.playVoice(baseContext, R.raw.training_start)
                Thread.sleep(1500)
                for (sec in 3 downTo 1) {
                    Thread.sleep(1000)
                    val soundId = TrainingVoiceManager.getNumbersVoice(sec)
                    TrainingVoiceManager.playVoice(baseContext, soundId)
                }
                Thread.sleep(1000)
                service.startEngine()
            }.start()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<ImageButton>(R.id.backspace).setOnClickListener {
            onBackPressed()
        }

        planId = intent.getByteExtra(EN_PLAN_ID, ERROR)
        levelNumber = intent.getByteExtra(EN_LEVEL_NUMBER, ERROR)
        trainingNumber = intent.getByteExtra(EN_TRAINING_NUMBER, ERROR)

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
            stopTrainingVoice()
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


    private fun setStartInfo() {
        findViewById<TextView>(R.id.topic).text = training?.getStepInfo(0)?.stepName
        findViewById<TextView>(R.id.time).text = training?.getStepTime(0).toString()
        findViewById<TextView>(R.id.all_time).text = formatTime(training?.getTime() ?: 0)
    }

    private fun connectService(service: TrainingEngineService) {
        initEngineListeners(service.engine)
        initButtonsOnClickListeners(service.engine)
    }

    private fun formatTime(time: Int): String {
        val del = 60
        val min = (time / del).toString()
        val sec = time % del
        val secF = if (sec < 10) "0${sec}" else sec
        return "$min:$secF"
    }

    private fun stopTrainingVoice() {
        Thread{
            MediaPlayer.create(this, R.raw.training_stop).start()
        }.start()
    }

    private fun initButtonsOnClickListeners(engine: TrainingEngine) {

        findViewById<CardView>(R.id.card_pause_and_play).setOnClickListener {
            engine.pushPauseOrPlay()
        }

        findViewById<CardView>(R.id.card_stop).setOnClickListener {
            engine.stopEngine()
            stopTrainingVoice()
            finish()
        }

        findViewById<CardView>(R.id.card_on_and_off).setOnClickListener {
            val idDrawable =
                if (engine.pushOnOrOffVibrator()) R.drawable.ic_vibration_off
                else R.drawable.ic_vibration_on
            findViewById<ImageView>(R.id.img_vibration).setImageResource(idDrawable)
        }
    }

    private fun initEngineListeners(engine: TrainingEngine) {
        engine.onPassedTimeListener = object : TrainingEngine.OnPassedTimeListener {
            override fun onPassedTime(stepTime: Int, allTime: Int) {
                findViewById<TextView>(R.id.all_time).text = formatTime(allTime)
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



}