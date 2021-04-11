package ru.kalistratov.breathtraining2.model.training

/**
 * The class of simple training.
 *
 * The training consist of [count] repetitions
 * inhales, two pauses and exhales in this order
 * [inhaleTime], [pauseTime], [exhaleTime].
 * All time in seconds.
 *
 * @param name the name of training.
 * @param number the number of training.
 * @param inhaleTime the inhale`s time in seconds.
 * @param exhaleTime the exhale`s time in seconds.
 * @param count the count of repetitions.
 * @property pauseTime is pause between inhale and exhale in seconds.
 */
class TriangleTraining(
    name: String,
    count: Byte,
    number: Byte,
    inhaleTime: Byte,
    exhaleTime: Byte,
    val pauseTime: Byte
) : Training(name, number, inhaleTime, exhaleTime, count) {

    override fun getStepInfo(index: Int): Step {
        return when (index) {
            0 -> Step.INHALE
            1 -> Step.PAUSE
            2 -> Step.EXHALE
            else -> getStepInfo(index % 3)
        }
    }

    override fun getStepTime(index: Int): Byte {
        return when (index) {
            0 -> inhaleTime
            1 -> pauseTime
            2 -> exhaleTime
            else -> getStepTime(index % 3)
        }
    }

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime + pauseTime) * count
    }
}