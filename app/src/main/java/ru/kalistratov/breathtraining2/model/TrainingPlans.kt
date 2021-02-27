package ru.kalistratov.breathtraining2.model

import android.content.Context
import android.util.Log
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ru.kalistratov.breathtraining2.model.TrainingPlans.plans
import java.lang.reflect.Type
import java.util.*


/**
 * The simple class of plan.
 *
 * @property name the name of training plan.
 * @property description the description of training plan.
 */
abstract class Plan(val name: String, val description: String, val type: String) {
    abstract fun levelsCount() : Int
}

/**
 * The abstract class of training plan.
 *
 * @property trainingsCount the level`s list of all trainings.
 */
abstract class TrainingPlan<E : Training>(name: String, description: String, type: String):
    Plan(name, description, type) {
    var levels : LinkedList<TrainingLevel<E>> = LinkedList()

    override fun levelsCount(): Int {
        return levels.size
    }
}

/**
 * The object consist of all training plans.
 *
 * @property plans the list of all training plans.
 */
object TrainingPlans : JsonDeserializer<TrainingPlans> {

    fun init(context: Context)  {
        if (plans.isNullOrEmpty()) {

            val stringBuilder = StringBuilder()
            val scanner = Scanner(context.assets.open("plans.json"))
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine()).append("\n")
            }
            val gson = GsonBuilder()
                .registerTypeAdapter(TrainingPlans::class.java, TrainingPlans)
                .create()
            gson.fromJson(stringBuilder.toString(), TrainingPlans::class.java)
        }
    }

    var plans : LinkedList<Plan> = LinkedList()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TrainingPlans {
        val g = GsonBuilder()
            .registerTypeAdapter(SquareBreathPlan::class.javaObjectType, SquareBreathPlan())
            .create()
        val array = json?.asJsonArray ?:
            throw NullPointerException("File is not consist json")
        plans = LinkedList()
        for(obj in array) {
            val plan = when (obj.asJsonObject.get("type").asString){
                "simple" -> g.fromJson(obj, SimpleBreathPlan::class.java)
                "square" -> g.fromJson(obj, SquareBreathPlan::class.java)
                else -> null
            } ?: continue
            plans.add(plan)
        }
        return this
    }


}

class SimpleBreathPlan(name: String, description: String, type: String) :
    TrainingPlan<SimpleTraining>(name, description, type)

class SquareBreathPlan(name: String, description: String, type: String) :
    TrainingPlan<SquareTraining>(name, description, type), JsonDeserializer<SquareBreathPlan> {
    constructor() : this("1234", "", "")

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SquareBreathPlan {
        val listType = object : TypeToken<LinkedList<TrainingLevel<SquareTraining>>>() {}.type
        val g = GsonBuilder().create()
        val newPlan = SquareBreathPlan()

        val levels = json?.asJsonObject?.get("levels")?.asJsonArray ?:
            throw NullPointerException("File is not consist json")
        newPlan.levels = g.fromJson(levels, listType)

        /*
        val levels = json?.asJsonObject?.get("levels")?.asJsonArray ?:
            throw NullPointerException("File is not consist json")
        for (level in levels) {
            val trainings = LinkedList<SquareTraining>()
            val trainingsArray = level.asJsonObject.get("trainings").asJsonArray
            for (training in trainingsArray) {
                trainings.add(g.fromJson(training, SquareTraining::class.java))
            }
            val num = level.asJsonObject.get("number").toString().toByte()
            val newLevel = TrainingLevel<SquareTraining>(num, trainings)
            newPlan.levels.add(newLevel)
        }

         */
        return newPlan
    }
}