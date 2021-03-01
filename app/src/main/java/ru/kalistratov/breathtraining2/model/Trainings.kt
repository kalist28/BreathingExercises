package ru.kalistratov.breathtraining2.model

import java.util.*

/**
 * The simple class of training.
 *
 * @property name the name of training.
 * @property count the count of repetitions.
 */
abstract class Training(name: String, var count: Byte, var number: Byte) {

    @Suppress("SENSELESS_COMPARISON")
    var name: String = name
        get() {
            return if (field == null || field == "null" || field.isEmpty())
                "Уровень $number"
            else field
        }

    abstract fun getTime() : Int
    override fun toString(): String {
        return "Training(name='$name', number=$number, count=$count)"
    }


}

/**
 * The class of level of training plan.
 *
 * @property number the number of level.
 * @property trainings the list of level`s trainings.
 */
class TrainingLevel<T : Training>(val number: Byte, val trainings: LinkedList<T>)

/**
 * The class of simple training.
 *
 * The training consist of [count] repetitions
 * inhales and exhales by [inhaleTime] and [exhaleTime] sec.
 *
 * @property inhaleTime the time in seconds of inhale.
 * @property exhaleTime the exhale`s time in seconds.
 */
open class SimpleTraining(name: String,
                          count: Byte,
                          number: Byte,
                          val inhaleTime : Byte,
                          val exhaleTime : Byte, ):
    Training(name, count, number) {
    constructor() : this("null", 0, 0,0,0)

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime) * count
    }
}

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
                     val secondPauseTime : Byte)
    : SimpleTraining(name, count, number, inhaleTime, exhaleTime){

    override fun getTime(): Int {
        return (inhaleTime + exhaleTime + firstPauseTime + secondPauseTime) * count
    }
}
