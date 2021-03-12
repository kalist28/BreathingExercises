package ru.kalistratov.breathtraining2.model.training.plan

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import ru.kalistratov.breathtraining2.model.training.SimpleTraining
import java.lang.reflect.Type
import java.util.*

class SimpleBreathPlan : ATrainingPlan<SimpleTraining>, JsonDeserializer<SimpleBreathPlan> {

    constructor(): super()

    constructor(planInfo: PlanInfo,
                levels : LinkedList<PlanLevel<SimpleTraining>>)
            : super (planInfo, levels)

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
}