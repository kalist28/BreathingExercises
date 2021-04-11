package ru.kalistratov.breathtraining2.model.training.plan

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement

/**
 * The simple class of plan.
 *
 * @property id the plan`s id.
 * @property name the name of training plan.
 * @property description the description of training plan.
 * @property type the type of trainings in plan.
 */
abstract class Plan (val id: Byte, val name: String, val description: String, val type: Byte) {

    /**
     * Total count levels in plan, accessible for the user.
     *
     * @return total count of levels in plan.
     */
    abstract fun levelsCount() : Int

    companion object {

        /** The simple training`s index. */
        const val SIMPLE: Byte = 1

        /** The square training`s index. */
        const val SQUARE: Byte = 2

        /** The triangle training`s index. */
        const val TRIANGLE: Byte = 3

        /** Gson object. */
        val gson: Gson = GsonBuilder().create()

        /**
         * Retrieves training information from a json element.
         *
         * @param json is json element.
         */
        fun createPlanInfo(json: JsonElement) : PlanNode {
            val jo = json.asJsonObject

            val id              = gson.fromJson(jo.get("id"), Byte::class.java)
            val type            = gson.fromJson(jo.get("type"), Byte::class.java)
            val name            = gson.fromJson(jo.get("name"), String::class.java)
            val description     = gson.fromJson(jo.get("description"), String::class.java)
            return PlanNode(id, name, description, type)
        }
    }
}