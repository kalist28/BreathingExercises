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
    : ATraining(name, count, number, inhaleTime, exhaleTime){

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime + pauseTime) * count
    }
}