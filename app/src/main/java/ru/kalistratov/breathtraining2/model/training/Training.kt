package ru.kalistratov.breathtraining2.model.training

/**
 * The simple class of training.
 *
 * @param name the name of training.
 * @property number the number of training.
 * @property inhaleTime the inhale`s time in seconds.
 * @property exhaleTime the exhale`s time in seconds.
 * @property count the count of repetitions.
 */
abstract class Training(
    name: String,
    var count: Byte,
    var number: Byte,
    val inhaleTime: Byte,
    val exhaleTime: Byte
) {

    /**
     * Describes all possible activities in a training.
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

    /**
     * The reception on specific step.
     *
     * @param index the count or number of steps, if steps is big,
     * then the remainder of the number of receptions is calculated.
     *
     * @return reception into ([Training.Step]])
     */
    abstract fun getStepInfo(index: Int): Step

    /**
     * The reception`s time on specific step.
     *
     * @param index the count or number of steps, if steps is big,
     * then the remainder of the number of receptions is calculated.
     *
     * @return total count ms.
     */
    abstract fun getStepTime(index: Int): Byte

    /**
     * The all time of training.
     *
     * @return total count ms.
     */
    abstract fun getTime(): Int

    override fun toString(): String {
        return "Training(name='$name', number=$number, count=$count)"
    }
}