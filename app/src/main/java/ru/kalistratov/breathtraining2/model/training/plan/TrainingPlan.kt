package ru.kalistratov.breathtraining2.model.training.plan

import ru.kalistratov.breathtraining2.model.training.Training
import java.util.*

/**
 * The abstract class of training plan.
 *
 * @param id the plan`s id.
 * @param name the name of training plan.
 * @param description the description of training plan.
 * @param type the type of trainings in plan.
 */
abstract class TrainingPlan<E : Training>(
    id: Byte,
    name: String,
    description: String,
    type: Byte
) : Plan(id, name, description, type) {

    constructor() : this(0, "null", "null", -1)

    constructor(planNode: PlanNode, levels: LinkedList<PlanLevel<E>>
    ) : this(planNode.id, planNode.name, planNode.description, planNode.type) {
        this.levels = levels
    }

    /**
     * The list of plan`s levels.
     */
    var levels: LinkedList<PlanLevel<E>> = LinkedList()

    override fun levelsCount(): Int {
        return levels.size
    }
}