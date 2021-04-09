package ru.kalistratov.breathtraining2.controller

object UserPreferences {
    lateinit var lastTraining: LastTraining
    fun init () {
        TODO("Класс сохранения")
    }
}


/**
 *
 * @param pId is plan`s id.
 * @param lNum is level`s number.
 * @param tNum is training`s number.
 */
class LastTraining(val pId: Byte, val lNum: Byte,val tNum: Byte)