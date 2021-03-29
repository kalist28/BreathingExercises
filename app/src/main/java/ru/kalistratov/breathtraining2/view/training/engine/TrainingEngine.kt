package ru.kalistratov.breathtraining2.view.training.engine

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import ru.kalistratov.breathtraining2.model.training.ATraining


/**
 * The template for Training`s engines.
 */
class TrainingEngine(val training: ATraining,
                     val context: Context) {

    /**
     * The phones` vibrator.
     * It is needed for additional notification of a change in breath intake.
     */
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    /**
     * The time for vibrator`s work.
     */
    private val milliseconds = 400L

    /**
     * Listener used to dispatch engine started event.
     */
    var onStartEngineListener: OnStartEngineListener? = null

    /**
     * Listener used to dispatch engine stop event.
     */
    var onStopEngineListener: OnStopEngineListener? = null

    /**
     * Listener used to dispatch event when a engine was work one second.
     */
    var onPassedTimeListener: OnPassedTimeListener? = null

    /**
     * Listener used to dispatch event engine apprehended work.
     */
    var onPauseListener: OnPauseListener? = null

    /**
     * Listener used to dispatch event when engine continue work.
     */
    var onContinueListener: OnContinueListener? = null

    /**
     * Listener used to dispatch event when breathing intake changes.
     */
    var onStepListener: OnStepListener? = null

    /**
     * True if the engine is paused.
     */
    private var isPause: Boolean = false

    /**
     * True if the engine is stopped.
     */
    private var isStop: Boolean = false

    /**
     * Remaining number of seconds for this step (breath intake).
     */
    private var stepTime: Int = 0

    /**
     * Number of receptions from the start of engine operation.
     */
    private var stepsCount: Int = 0

    /**
     * Time from the start of engine operation.
     * Not considering the pause.
     */
    private var allTime = training.getTime()

    /** Engine starting. */
    fun startEngine() {
        startTimer()
        onStartEngineListener?.onStartEngine()
    }

    /** Suspend the engine. */
    fun pauseEngine() {
        isPause = true
        onPauseListener?.onPause()
        Toast.makeText(context, "ENGINE PAUSE", Toast.LENGTH_LONG).show()
    }

    /** Continue from where engine stopped. */
    fun continueEngine() {
        isPause = false
        onContinueListener?.onContinue()
        Toast.makeText(context, "ENGINE CONT", Toast.LENGTH_LONG).show()
    }

    /** Engine stopping. */
    fun stopEngine() {
        isStop = true
        onStopEngineListener?.onStopEngine()
        Toast.makeText(context, "ENGINE STOP", Toast.LENGTH_LONG).show()
    }

    fun pushPauseOrPlay() {
        if (isPause) continueEngine()
        else pauseEngine()
    }

    /** The even when the time for the respiratory intake ends. */
    private fun onProgress() {
        val step = training.getStepName(stepsCount)
        stepTime += training.getStepTime(stepsCount)
        onStepListener?.onStep(step)

        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // API 26
                vibrator.vibrate(
                        VibrationEffect.createOneShot(
                                milliseconds,
                                VibrationEffect.DEFAULT_AMPLITUDE
                        )
                )
            } else {
                // This method was deprecated in API level 26
                @Suppress("DEPRECATION")
                vibrator.vibrate(milliseconds)
            }
        }
    }

    /** Event after one second. */
    private fun onPassedSecond() {
        onPassedTimeListener?.onPassedTime(stepTime, allTime)
    }

    private fun startTimer() {
        stepTime += training.getStepTime(stepsCount)
        Thread {
            while (allTime-- != 0) {
                Thread.sleep(500)
                if (isStop) break
                if (isPause) continue
                Thread.sleep(500)
                stepTime--
                if (stepTime < 0) throw Exception("Timer step error")
                if (stepTime == 0) {
                    stepsCount++
                    onProgress()
                }
                onPassedSecond()
            }
            onStopEngineListener?.onStopEngine()
        }.start()
    }

    /**
     * Interface definition for a callback to be invoked when a engine is started.
     */
    interface OnStartEngineListener {
        /**
         * Called when a engine started.
         */
        fun onStartEngine()
    }

    /**
     * Interface definition for a callback to be invoked when a engine is stop.
     */
    interface OnStopEngineListener {
        /**
         * Called when a engine stop.
         */
        fun onStopEngine()
    }

    /**
     * Interface definition for a callback to be invoked when a engine worked one second.
     */
    interface OnPassedTimeListener {
        /**
         * Called when a engine worked one second.
         */
        fun onPassedTime(stepTime: Int, allTime: Int)
    }

    /**
     * Interface definition for a callback to be invoked when a engine is apprehended.
     */
    interface OnPauseListener {
        /**
         * Called when a engine is apprehended.
         */
        fun onPause()
    }

    /**
     * Interface definition for a callback to be invoked when a engine is continue work.
     */
    interface OnContinueListener {
        /**
         * Called when a engine is continue work.
         */
        fun onContinue()
    }

    /**
     * Interface definition for a callback to be invoked when breathing intake changes.
     */
    interface OnStepListener {
        /**
         * Called when breathing intake changes.
         */
        fun onStep(newName: String)
    }
}