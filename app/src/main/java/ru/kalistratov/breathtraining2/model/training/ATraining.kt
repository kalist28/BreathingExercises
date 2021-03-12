package ru.kalistratov.breathtraining2.model.training

/**
 * The simple class of training.
 *
 * @property name the name of training.
 * @property count the count of repetitions.
 */
abstract class ATraining(name: String,
                         var count: Byte,
                         var number: Byte,
                         val inhaleTime : Byte,
                         val exhaleTime : Byte){

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