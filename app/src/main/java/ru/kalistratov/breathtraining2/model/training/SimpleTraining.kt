package ru.kalistratov.breathtraining2.model.training

/**
 * The class of simple training.
 *
 * The training consist of [count] repetitions
 * inhales and exhales by [inhaleTime] and [exhaleTime] sec.
 */
class SimpleTraining(name: String,
                     count: Byte,
                     number: Byte,
                     inhaleTime : Byte,
                     exhaleTime : Byte):
        Training(name, count, number, inhaleTime, exhaleTime) {
    constructor() : this("null", 0, 0,0,0)

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime) * count
    }

    override fun getStepInfo(index: Int): Step {
        return when(index) {
            0 -> Step.INHALE
            1 -> Step.EXHALE
            else -> getStepInfo(index % 2)
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