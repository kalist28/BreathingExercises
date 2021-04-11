package ru.kalistratov.breathtraining2.model.training.plan

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import ru.kalistratov.breathtraining2.model.training.SquareTraining
import java.lang.reflect.Type
import java.util.*

/**
 * The trainings plan based on [SquareTraining].
 */
class SquareBreathPlan : TrainingPlan<SquareTraining>, JsonDeserializer<SquareBreathPlan> {

    constructor(): super()

    constructor(planNode: PlanNode,
                levels : LinkedList<PlanLevel<SquareTraining>>)
            : super (planNode, levels)

    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
    ): SquareBreathPlan {
        val listType = object : TypeToken<LinkedList<PlanLevel<SquareTraining>>>() {}.type
        val jo = json?.asJsonObject ?: throw NullPointerException("File is not consist json")

        val levelsJson = jo.get("levels")?.asJsonArray
        val levels : LinkedList<PlanLevel<SquareTraining>> = gson.fromJson(levelsJson, listType)
        return SquareBreathPlan(createPlanInfo(json), levels)
    }

    override fun toString(): String {
        return "SquareBreathPlan()"
    }
}