package ru.kalistratov.breathtraining2.model.training.plan

/**
 * The plan`s node, describing superficial basic information about the plan.
 *
 * @property id the plan`s id.
 * @property name the name of training plan.
 * @property description the description of training plan.
 * @property type the type of trainings in plan.
 */
class PlanNode(val id: Byte, val name: String, val description: String, val type: Byte)
