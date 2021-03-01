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
abstract class TrainingPlan<E : Training> (name: String,
                                           description: String,
                                           type: String)
    : Plan(name, description, type) {

    constructor() : this("null", "null", "null")

    constructor(name: String,
                description: String,
                type: String,
                levels : LinkedList<TrainingLevel<E>>)
            : this(name, description, type) {
        this.levels = levels
    }
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

class SquareBreathPlan : TrainingPlan<SquareTraining>, JsonDeserializer<SquareBreathPlan> {

    constructor(): super()

    constructor(name: String,
                description: String,
                type: String,
                levels : LinkedList<TrainingLevel<SquareTraining>>)
            : super (name, description, type, levels)

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SquareBreathPlan {
        val listType = object : TypeToken<LinkedList<TrainingLevel<SquareTraining>>>() {}.type
        val g = GsonBuilder().create()

        val jo = json?.asJsonObject ?: throw NullPointerException("File is not consist json")
        
        val levelsJson = jo.get("levels")?.asJsonArray
        val levels : LinkedList<TrainingLevel<SquareTraining>> = g.fromJson(levelsJson, listType)
        val type : String           = g.fromJson(jo.get("type"), String::class.java)
        val name : String           = g.fromJson(jo.get("name"), String::class.java)
        val description : String    = g.fromJson(jo.get("description"), String::class.java)
        return SquareBreathPlan(name, description, type, levels)
    }
}