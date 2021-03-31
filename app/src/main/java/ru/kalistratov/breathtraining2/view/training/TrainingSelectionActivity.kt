package ru.kalistratov.breathtraining2.view.training

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.adapter.LevelSelectionAdapter
import ru.kalistratov.breathtraining2.controller.adapter.OnLevelSelectListener
import ru.kalistratov.breathtraining2.controller.adapter.TrainingSelectionAdapter
import ru.kalistratov.breathtraining2.model.training.plan.*
import java.lang.Exception
import java.util.*

class TrainingSelectionActivity : AppCompatActivity(R.layout.activity_training_selection) {

    private var planId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<ImageButton>(R.id.backspace).setOnClickListener {
            onBackPressed()
        }

        planId = intent.getIntExtra("planId", -1)

        if (planId == -1) {
            finish()
            return
        }

        MobileAds.initialize(this)
    }

    override fun onResume() {
        super.onResume()

        val trainingPlan = when (TrainingPlans.plans[planId]) {
            is SimpleBreathPlan -> TrainingPlans.plans[planId] as SimpleBreathPlan
            is SquareBreathPlan -> TrainingPlans.plans[planId] as SquareBreathPlan
            is TriangleBreathPlan -> TrainingPlans.plans[planId] as TriangleBreathPlan
            else -> throw Exception(" is`t plan.")
        }

        Log.e("TAG", " ${trainingPlan.id} onResume: $trainingPlan : $planId" )

        if (trainingPlan.levels.size == 0) {
            finish()
            return
        }

        val topic = findViewById<TextView>(R.id.topic)
        topic.setTextColor(resources.getColor(R.color.home))
        topic.text = trainingPlan.name
        topic.gravity = Gravity.CENTER

        val levelList: RecyclerView = findViewById(R.id.levels)
        val trainingList: RecyclerView = findViewById(R.id.trainings)

        val levelAdapter = LevelSelectionAdapter(trainingPlan.levels,this)
        levelList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        levelList.adapter = levelAdapter

        levelAdapter.onLevelSelectListener = OnLevelSelectListener {
            val levelTopic = findViewById<TextView>(R.id.levelTopic)
            val levelNum = getString(R.string.level) + " ${it.number}"
            levelTopic.text = levelNum
            trainingList.adapter = TrainingSelectionAdapter(LinkedList(it.trainings), it.number, trainingPlan.id, baseContext)
        }
        val startLevel = trainingPlan.levels.first
        trainingList.layoutManager = LinearLayoutManager(this)
        trainingList.adapter = TrainingSelectionAdapter(LinkedList(startLevel.trainings), startLevel.number, trainingPlan.id, baseContext)
    }
}