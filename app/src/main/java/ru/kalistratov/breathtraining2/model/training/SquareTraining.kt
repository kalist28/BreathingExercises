package ru.kalistratov.breathtraining2.model.training

/**
 * The class of simple training.
 *
 * The training consist of [count] repetitions
 * inhales, two pauses and exhales in this order
 * [inhaleTime], [firstPauseTime], [exhaleTime], [secondPauseTime].
 * All time in seconds.
 *
 * @property inhaleTime the time in seconds of inhale.
 * @property exhaleTime the exhale`s time in seconds.
 */
class SquareTraining(name: String,
                     count: Byte,
                     number: Byte,
                     inhaleTime: Byte,
                     exhaleTime: Byte,
                     val firstPauseTime : Byte,
                     val secondPauseTime : Byte):
        Training(name, count, number, inhaleTime, exhaleTime) {

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime + firstPauseTime + secondPauseTime) * count
    }

    override fun getStepInfo(index: Int): Step {
        return when(index) {
            0 -> Step.INHALE
            1 -> Step.PAUSE
            2 -> Step.EXHALE
            3 -> Step.PAUSE
            else -> getStepInfo(index % 4)
        }
    }

    override fun getStepTime(index: Int): Byte {
        return when(index) {
            0 -> inhaleTime
            1 -> firstPauseTime
            2 -> exhaleTime
            3 -> secondPauseTime
            else -> getStepTime(index % 4)
        }
    }
}