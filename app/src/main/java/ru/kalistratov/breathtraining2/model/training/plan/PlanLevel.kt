package ru.kalistratov.breathtraining2.model.training.plan

import ru.kalistratov.breathtraining2.model.training.ATraining
import java.util.*

/**
 * The class of level of training plan.
 *
 * @property number the number of level.
 * @property trainings the list of level`s trainings.
 */
class PlanLevel<T : ATraining>(val number: Byte, val trainings: LinkedList<T>)