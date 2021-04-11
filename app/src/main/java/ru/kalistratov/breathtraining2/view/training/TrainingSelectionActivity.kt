package ru.kalistratov.breathtraining2.view.training

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.CenterLayoutManager
import ru.kalistratov.breathtraining2.controller.TrainingNode
import ru.kalistratov.breathtraining2.controller.UserPreferences
import ru.kalistratov.breathtraining2.controller.adapter.LevelSelectionAdapter
import ru.kalistratov.breathtraining2.controller.adapter.TrainingSelectionAdapter
import ru.kalistratov.breathtraining2.model.training.plan.*
import java.util.*

class TrainingSelectionActivity : AppCompatActivity(R.layout.activity_training_selection) {

    private lateinit var levelRV: RecyclerView
    private lateinit var trainingsRV: RecyclerView
    private lateinit var activeTraining: TrainingNode
    private var planId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        planId = intent.getIntExtra(getString(R.string.plan_id), -1)

        if (planId == -1) {
            finish()
            return
        }

        MobileAds.initialize(this)

        findViewById<ImageButton>(R.id.backspace).setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

        val trainingPlan = when (TrainingPlans.plans[planId]) {
            is SimpleBreathPlan -> TrainingPlans.plans[planId] as SimpleBreathPlan
            is SquareBreathPlan -> TrainingPlans.plans[planId] as SquareBreathPlan
            is TriangleBreathPlan -> TrainingPlans.plans[planId] as TriangleBreathPlan
            else -> throw Exception(" is`t plan.")
        }

        if (trainingPlan.levels.size == 0) {
            finish()
            return
        }

        activeTraining = UserPreferences.findActiveTrainingInPlane(trainingPlan.id)

        val topic = findViewById<TextView>(R.id.topic)
        topic.setTextColor(resources.getColor(R.color.home))
        topic.text = trainingPlan.name
        topic.gravity = Gravity.CENTER

        levelRV = findViewById(R.id.levels)
        trainingsRV = findViewById(R.id.trainings)

        initTrainingsList(trainingPlan.levels.first, trainingPlan.id)
        initLevelList(trainingPlan)

    }

    private fun initTrainingsList(startLevel: PlanLevel<*>, planId: Byte) {
        trainingsRV.layoutManager = LinearLayoutManager(this)
        trainingsRV.adapter = TrainingSelectionAdapter(
            LinkedList(startLevel.trainings),
            startLevel.number,
            planId,
            baseContext
        )
        trainingsRV.post {
            trainingsRV.smoothScrollToPosition(activeTraining.trainingNum.toInt())
        }
    }

    private fun initLevelList(trainingPlan: TrainingPlan<*>) {
        val levelAdapter = LevelSelectionAdapter(trainingPlan.levels, this)
        levelRV.layoutManager = CenterLayoutManager(this, RecyclerView.HORIZONTAL, false)
        levelRV.adapter = levelAdapter

        levelAdapter.onLevelSelectListener = LevelSelectionAdapter.OnLevelSelectListener {
            val levelTopic = findViewById<TextView>(R.id.levelTopic)
            val levelNum = getString(R.string.level) + " ${it.number}"
            levelTopic.text = levelNum
            trainingsRV.adapter = TrainingSelectionAdapter(
                LinkedList(it.trainings),
                it.number,
                trainingPlan.id,
                baseContext
            )
            if (it.number == activeTraining.levelNum)
                (trainingsRV.adapter as TrainingSelectionAdapter)
                    .activeTraining = activeTraining.trainingNum.toInt()
        }

        levelAdapter.activateItem(activeTraining.levelNum.toInt() - 1)
    }
}