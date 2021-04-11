package ru.kalistratov.breathtraining2.controller

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * Object for load and save user progress and setting.
 */
object UserPreferences {

    /**
     * The file name, in which save all info in JSON format.
     */
    private const val FILE_NAME: String = "properties.txt"

    /**
     * The object [Gson] for work with JSON format.
     */
    private val gson = Gson()

    /**
     * The tree in which all completed trainings.
     * Sorted by plan, level and workout number from lowest to highest.
     */
    var processNoteSet = TreeSet(NoteComparator())

    /**
     * Initialize object.
     *
     * @param context the application context.
     */
    fun init(context: Context) {
        gson.toJson(this)

        //save(context)

        load(context)
    }

    /**
     * Find active training inn plane.
     *
     * @param planId - the plan`s id.
     *
     * @return training info in [TrainingNode], this is active training for plan`s id.
     */
    public fun findActiveTrainingInPlane(planId: Byte): TrainingNode {
        val set = TreeSet(NoteComparator())
        for (n in processNoteSet) {
            if (n.planId == planId) set.add(n)
        }
        return set.last()
    }

    /**
     * Save all info in file.
     *
     * @param context the application context.
     */
    private fun save(context: Context) {
        val fos = context.openFileOutput(
            FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        processNoteSet.add(TrainingNode(1, 3, 1))
        val str = gson.toJson(processNoteSet) + "&"
        fos.write(str.toByteArray())
        fos.close()
    }

    /**
     * Load info from file.
     *
     * @param context the application context.
     */
    private fun load(context: Context) {
        val fin = context.openFileInput(FILE_NAME)
        val bytes = ByteArray(fin.available())
        fin.read(bytes)
        val text = String(bytes).split("&")
        val listType = object : TypeToken<TreeSet<TrainingNode>>() {}.type
        processNoteSet.clear()
        processNoteSet.addAll(gson.fromJson(text[0], listType))
    }

}


/**
 * Class describes the workout completed.
 *
 * @param planId is plan`s id.
 * @param levelNum is level`s number.
 * @param trainingNum is training`s number.
 */
class TrainingNode(
    val planId: Byte,
    val levelNum: Byte,
    val trainingNum: Byte
) : Comparable<TrainingNode> {

    /**
     * Create number value from:
     * plan id - [planId],
     * level number [levelNum],
     * training number [trainingNum].
     */
    fun getValue(): Int {
        return String.format("%d%d%d", planId, levelNum, trainingNum).toInt()
    }

    override fun toString(): String {
        return "Note(pId=$planId, lNum=$levelNum, tNum=$trainingNum)\n"
    }

    override fun compareTo(other: TrainingNode): Int {
        return compareValues(getValue(), other.getValue())
    }
}

/**
 * Comparator for [TrainingNode].
 */
class NoteComparator : Comparator<TrainingNode> {
    override fun compare(o1: TrainingNode, o2: TrainingNode): Int {
        return o1.compareTo(o2)
    }
}