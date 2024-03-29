package ru.kalistratov.breathtraining2.model.training.plan

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import ru.kalistratov.breathtraining2.model.training.SimpleTraining
import ru.kalistratov.breathtraining2.model.training.TriangleTraining
import java.lang.reflect.Type
import java.util.*

/**
 * The trainings plan based on [TriangleTraining].
 */
class TriangleBreathPlan : TrainingPlan<TriangleTraining>, JsonDeserializer<TriangleBreathPlan> {

    constructor(): super()

    constructor(planNode: PlanNode,
                levels : LinkedList<PlanLevel<TriangleTraining>>)
            : super (planNode, levels)

    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?)
            : TriangleBreathPlan {
        val listType = object : TypeToken<LinkedList<PlanLevel<TriangleTraining>>>() {}.type
        val jo = json?.asJsonObject ?: throw NullPointerException("File is not consist json")

        val levelsJson = jo.get("levels")?.asJsonArray
        val levels : LinkedList<PlanLevel<TriangleTraining>> = gson.fromJson(levelsJson, listType)
        return TriangleBreathPlan(createPlanInfo(json), levels)
    }

    override fun toString(): String {
        return "TriangleBreathPlan()"
    }
}