package ru.kalistratov.breathtraining2.view.training.engine

import android.util.Log
import ru.kalistratov.breathtraining2.model.training.ATraining
import java.lang.Exception

/**
 * The template for Training`s engines.
 */
class TrainingEngine(val engineActions: EngineActions,
                     val training: ATraining) {

    /** True if the engine is paused*/
    private var isPause: Boolean = false

    /** True if the engine is stopped*/
    private var isStop: Boolean = false

    /** Remaining number of seconds for this step (breath intake). */
    private var stepTime: Int = 0

    /** Number of receptions from the start of engine operation. */
    private var stepsCount: Int = 0

    /** Engine starting. */
    fun startEngine() {
        startTimer()
        engineActions.onStartEngine()
    }

    /** Suspend the engine. */
    fun pauseEngine() {
        isPause = true
    }

    /** Continue from where engine stopped. */
    fun continueEngine() {
        isPause = false
    }

    /** Engine stopping. */
    fun stopEngine() {
        isStop = true
        engineActions.onStopEngine()
    }

    /** The even when the time for the respiratory intake ends. */
    fun onProgress() {
        val step = training.getStepName(stepsCount)
        stepTime += training.getStepTime(stepsCount)
        engineActions.onStepName(step)

        TODO("Добавить вибратор")
    }

    /** Event after one second. */
    fun onPassedSecond() {
        engineActions.onPassedSecond(stepTime)
    }

    private fun startTimer() {
        var count = training.getTime()
        stepTime += training.getStepTime(stepsCount)
        Thread {
            while (count-- != 0) {
                if (isStop) break
                Thread.sleep(1000)
                if (isPause) continue
                stepTime--
                if (stepTime < 0) throw Exception("Timer step error")
                if (stepTime == 0) {
                    stepsCount++
                    onProgress()
                }
                onPassedSecond();
            }
            engineActions.onStopEngine()
        }.start()
    }
}