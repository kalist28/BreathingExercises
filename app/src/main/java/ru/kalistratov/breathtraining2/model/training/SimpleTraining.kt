package ru.kalistratov.breathtraining2.model.training

/**
 * The class of simple training.
 *
 * The training consist of [count] repetitions
 * inhales and exhales by [inhaleTime] and [exhaleTime] sec.
 *
 * @param name the name of training.
 * @param number the number of training.
 * @param inhaleTime the inhale`s time in seconds.
 * @param exhaleTime the exhale`s time in seconds.
 * @param count the count of repetitions.
 */
class SimpleTraining(
    name: String,
    count: Byte,
    number: Byte,
    inhaleTime: Byte,
    exhaleTime: Byte
) :
    Training(name, number, inhaleTime, exhaleTime, count) {
    constructor() : this("null", 0, 0, 0, 0)

    override fun getStepInfo(index: Int): Step {
        return when (index) {
            0 -> Step.INHALE
            1 -> Step.EXHALE
            else -> getStepInfo(index % 2)
        }
    }

    override fun getStepTime(index: Int): Byte {
        return when (index) {
            0 -> inhaleTime
            1 -> exhaleTime
            else -> getStepTime(index % 2)
        }
    }

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime) * count
    }
}