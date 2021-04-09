package ru.kalistratov.breathtraining2.view.training.engine

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import ru.kalistratov.breathtraining2.controller.VoiceManager
import ru.kalistratov.breathtraining2.model.training.Training
import java.util.*

/**
 * The background service, for start engine in separate thread and connect engine with activity.
 * All engine control and event is realized here.
 */
class TrainingEngineService : Service() {

    /** Class for connect service with activity. */
    inner class Binder : android.os.Binder() {
        fun service() : TrainingEngineService
        {
            return this@TrainingEngineService
        }
    }

    /**
     * Listener used to dispatch engine started event.
     */
    var onStartEngineListener: TrainingEngine.OnStartEngineListener? = null

    /**
     * Listener used to dispatch engine ended event.
     */
    var onEndEngineListener: TrainingEngine.OnEndEngineListener? = null

    /**
     * Listener used to dispatch engine stop event.
     */
    var onStopEngineListener: TrainingEngine.OnStopEngineListener? = null

    /**
     * Listener used to dispatch event when a engine was work one second.
     */
    var onPassedTimeListener: TrainingEngine.OnPassedTimeListener? = null

    /**
     * Listener used to dispatch event engine apprehended work.
     */
    var onPauseListener: TrainingEngine.OnPauseListener? = null

    /**
     * Listener used to dispatch event when engine continue work.
     */
    var onContinueListener: TrainingEngine.OnContinueListener? = null

    /**
     * Listener used to dispatch event when breathing intake changes.
     */
    var onStepListener: TrainingEngine.OnStepListener? = null

    /**
     * Unkillable notification.
     * Notifies the user to read his location in real time.
     */
    private var notification: EngineNotification? = null

    /**
     * System notification manager.
     */
    private var notificationManagerCompat: NotificationManager? = null

    /**
     * Consist this service for connect with activity.
     */
    private lateinit var binder: Binder

    /**
     * The training engine object.
     */
    private lateinit var engine: TrainingEngine

    /**
     * Step name at the moment.
     */
    private var stepName: String = "Старт"

    /**
     * Initialization service.
     * @param training - the training with params info.
     */
    fun init(training: Training) {
        engine = TrainingEngine(training, this)

        initEngineListeners()
        initNotification()
    }

    override fun onCreate() {
        super.onCreate()
        binder = Binder()
    }

    override fun onDestroy() {
        stopForeground(true)
        engine.stopEngine()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(
        intent: Intent,
        flags: Int,
        startId: Int): Int {
        try {
            if (intent.getStringExtra("action") == EngineNotification.STOP_ENGINE) {
                stopEngine()
                stopSelf()
            }
        } catch (e: NullPointerException) { }
        return START_NOT_STICKY
    }

    /**
     * Start engine.
     */
    fun startEngine() {
        engine.startEngine()
    }

    /**
     * Stop engine.
     */
    fun stopEngine() {
        engine.stopEngine()
    }

    /**
     * User press button and this event must set engine pause or engine continue.
     */
    fun pushPauseOrContinue() {
        engine.pushPauseOrContinue()
    }

    /**
     * User press button and this event must phone vibrator on or off.
     */
    fun pushOnOrOffVibrator(): Boolean {
        return engine.pushOnOrOffVibrator()
    }

    /**
     * Initialize notification for start foreground service.
     */
    private fun initNotification() {
        notification = EngineNotification(baseContext)

        notificationManagerCompat = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        startForeground(101, notification!!.build())
    }

    /**
     * Initialize event and add in them some actions.
     */
    private fun initEngineListeners() {
        engine.onStartEngineListener = object : TrainingEngine.OnStartEngineListener {
            override fun onStartEngine() {
                onStartEngineListener?.onStartEngine()
            }
        }

        engine.onEndEngineListener = object : TrainingEngine.OnEndEngineListener {
            override fun onEndEngine() {
                onEndEngineListener?.onEndEngine()
                VoiceManager.playVoice(baseContext, VoiceManager.Voice.TRAINING_END)
            }
        }

        engine.onStopEngineListener = object : TrainingEngine.OnStopEngineListener {
            override fun onStopEngine() {
                onStopEngineListener?.onStopEngine()
                VoiceManager.playVoice(baseContext, VoiceManager.Voice.TRAINING_STOP)
            }
        }

        engine.onPauseListener = object : TrainingEngine.OnPauseListener {
            override fun onPause() {
                onPauseListener?.onPause()
            }
        }

        engine.onContinueListener = object : TrainingEngine.OnContinueListener {
            override fun onContinue() {
                onContinueListener?.onContinue()
            }
        }

        engine.onStepListener = object : TrainingEngine.OnStepListener {
            override fun onStep(newName: String) {
                onStepListener?.onStep(newName)
                stepName = newName
            }
        }

        engine.onPassedTimeListener = object : TrainingEngine.OnPassedTimeListener {
            override fun onPassedTime(stepTime: Int, formatTime: String) {
                onPassedTimeListener?.onPassedTime(stepTime, formatTime)

                val content = java.lang.String.format(
                    Locale.ENGLISH,
                    "%s - %d\nОсталось %s",
                    stepName.toUpperCase(Locale.ROOT), stepTime, formatTime
                )
                notification?.setContentText(content)
                notificationManagerCompat!!.notify(101, notification!!.build())
            }

        }
    }
}