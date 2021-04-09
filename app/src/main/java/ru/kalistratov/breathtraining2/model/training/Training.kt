package ru.kalistratov.breathtraining2.model.training

/**
 * The simple class of training.
 *
 * @property name the name of training.
 * @property count the count of repetitions.
 */
abstract class Training(name: String,
                        var count: Byte,
                        var number: Byte,
                        val inhaleTime : Byte,
                        val exhaleTime : Byte) {

    /**
     * Describes all possible activities in a workout.
     */
    enum class Step(
        val stepName: String,
    ) {
        INHALE("вдох"),
        EXHALE("выдох"),
        PAUSE("пауза")
    }

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

    abstract fun getStepInfo(index: Int): Step

    abstract fun getStepTime(index: Int): Byte
}