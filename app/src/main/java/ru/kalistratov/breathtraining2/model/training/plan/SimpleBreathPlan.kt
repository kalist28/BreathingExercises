package ru.kalistratov.breathtraining2.model.training.plan

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import ru.kalistratov.breathtraining2.model.training.SimpleTraining
import java.lang.reflect.Type
import java.util.*

/**
 * The trainings plan based on [SimpleTraining].
 */
class SimpleBreathPlan : TrainingPlan<SimpleTraining>, JsonDeserializer<SimpleBreathPlan> {

    constructor(): super()

    constructor(planNode: PlanNode,
                levels : LinkedList<PlanLevel<SimpleTraining>>)
            : super (planNode, levels)

    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
    ): SimpleBreathPlan {
        val listType = object : TypeToken<LinkedList<PlanLevel<SimpleTraining>>>() {}.type
        val jo = json?.asJsonObject ?: throw NullPointerException("File is not consist json")

        val levelsJson = jo.get("levels")?.asJsonArray
        val levels : LinkedList<PlanLevel<SimpleTraining>> = gson.fromJson(levelsJson, listType)
        return SimpleBreathPlan(createPlanInfo(json), levels)
    }

    override fun toString(): String {
        return "SimpleBreathPlan()"
    }


}