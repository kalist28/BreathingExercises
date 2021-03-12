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
        ATraining(name, count, number, inhaleTime, exhaleTime) {
    constructor() : this("null", 0, 0,0,0)

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime) * count
    }
}