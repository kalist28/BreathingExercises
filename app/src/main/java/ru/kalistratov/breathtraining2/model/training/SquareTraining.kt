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
        ATraining(name, count, number, inhaleTime, exhaleTime) {

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime + firstPauseTime + secondPauseTime) * count
    }

    override fun getStepName(index: Int): String {
        return when(index) {
            0 -> INHALE
            1 -> PAUSE
            2 -> EXHALE
            3 -> PAUSE
            else -> getStepName(index % 4)
        }
    }
    override fun getStepTime(index: Int): Byte {
        return when(index) {
            0 -> inhaleTime
            1 -> exhaleTime
            else -> getStepTime(index % 2)
        }
    }
}