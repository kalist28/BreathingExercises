package ru.kalistratov.breathtraining2.model.training

/**
 * The class of simple training.
 *
 * The training consist of [count] repetitions
 * inhales, two pauses and exhales in this order
 * [inhaleTime], [pauseTime], [exhaleTime].
 * All time in seconds.
 *
 * @property pauseTime is pause between inhale and exhale.
 */
class TriangleTraining(name: String,
                       count: Byte,
                       number: Byte,
                       inhaleTime: Byte,
                       exhaleTime: Byte,
                       val pauseTime : Byte)
    : Training(name, count, number, inhaleTime, exhaleTime){

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime + pauseTime) * count
    }

    override fun getStepInfo(index: Int): Step {
        return when(index) {
            0 -> Step.INHALE
            1 -> Step.PAUSE
            2 -> Step.EXHALE
            else -> getStepInfo(index % 3)
        }
    }

    override fun getStepTime(index: Int): Byte {
        return when(index) {
            0 -> inhaleTime
            1 -> pauseTime
            2 -> exhaleTime
            else -> getStepTime(index % 3)
        }
    }
}