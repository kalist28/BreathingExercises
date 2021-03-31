package ru.kalistratov.breathtraining2.model.training.plan

import ru.kalistratov.breathtraining2.model.training.Training
import java.util.*

/**
 * The abstract class of training plan.
 */
abstract class ATrainingPlan<E : Training> (
        id: Byte, name: String, description: String, type: Byte)
    : APlan(id, name, description, type) {

    constructor() : this(0, "null", "null", -1)

    constructor(planInfo: PlanInfo,
                levels : LinkedList<PlanLevel<E>>)
            : this(planInfo.id, planInfo.name, planInfo.description, planInfo.type) {
        this.levels = levels
    }

    var levels : LinkedList<PlanLevel<E>> = LinkedList()

    override fun levelsCount(): Int {
        return levels.size
    }
}