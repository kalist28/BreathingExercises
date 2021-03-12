package ru.kalistratov.breathtraining2.model.training.plan

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.kalistratov.breathtraining2.model.training.ATraining
import java.lang.reflect.Type
import java.util.*

/**
 * The object consist of all training plans.
 *
 * @property plans the list of all training plans.
 */
object TrainingPlans : JsonDeserializer<TrainingPlans> {

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
            val plan = when (obj.asJsonObject.get("type").asByte){
                APlan.SIMPLE     -> g.fromJson(obj, SimpleBreathPlan::class.java)
                APlan.SQUARE     -> g.fromJson(obj, SquareBreathPlan::class.java)
                APlan.TRIANGLE   -> g.fromJson(obj, TriangleBreathPlan::class.java)
                else -> throw Exception("unknown training type.")
            } ?: continue
            plans.add(plan)
        }
        return this
    }

    /** Initialize object. */
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

    /** The list pf level`s trainings. */
    var plans : LinkedList<APlan> = LinkedList()

    /**
     * Find the training by: plan`s id, level`s number and training`s number.
     *
     * @param pId is plan`s id.
     * @param lNum is level`s number.
     * @param tNum is training`s number.
     */
    fun findTrainingInPlan(pId: Int, lNum: Int, tNum: Int) : ATraining? {
        var plan: ATrainingPlan<*>? = null
        var level: PlanLevel<*>? = null
        var training: ATraining? = null

        for (p in plans) {
            if (p.id.toInt() == pId) {
                plan = p as ATrainingPlan<*>
                break
            }
        }

        for (l in plan?.levels!!) {
            if (l.id.toInt() == lNum) {
                level = l
                break
            }
        }

        for (t in level?.trainings!!) {
            if (t.number.toInt() == tNum) {
                training = t
                break
            }
        }

        return training
    }
}