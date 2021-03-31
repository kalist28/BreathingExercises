package ru.kalistratov.breathtraining2.controller.adapter

import android.content.Context
import android.media.MediaPlayer
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.model.training.Training

/**
 * Voice class for training steps and the following techniques.
 */
object TrainingVoiceManager {

    fun playVoice(context: Context, soundId: Int) {
        Thread{
            MediaPlayer.create(context, soundId).start()
        }.start()
    }

    fun actingFollowingActions(time: Int, training: Training, steps: Int, context: Context) { // , stepType: Training.Step
        val nextStep = steps + 1
        var soundId = -1
        when {
            time > 5 -> return
            time == 5 -> soundId = getNextStepVoice(training.getStepInfo(nextStep))
            time == 0 -> soundId = getStepVoice (training.getStepInfo(nextStep))
            time <= 3 -> soundId = getNumbersVoice(time)
        }

        if (soundId != -1) playVoice(context, soundId)

    }

    fun getNumbersVoice(n: Int) : Int {
        return when (n) {
            3 ->  R.raw.tree
            2 ->  R.raw.two
            1 ->  R.raw.one
            else -> -1
        }
    }

    fun getStepVoice(s: Training.Step) : Int {
        return when (s) {
            Training.Step.PAUSE  -> Training.Step.PAUSE.soundId
            Training.Step.INHALE -> Training.Step.INHALE.soundId
            Training.Step.EXHALE -> Training.Step.EXHALE.soundId
        }
    }

    fun getNextStepVoice(s: Training.Step) : Int {
        return when (s) {
            Training.Step.PAUSE  -> R.raw.pause_through
            Training.Step.INHALE -> R.raw.inhale_through
            Training.Step.EXHALE -> R.raw.exhale_through
        }
    }
}