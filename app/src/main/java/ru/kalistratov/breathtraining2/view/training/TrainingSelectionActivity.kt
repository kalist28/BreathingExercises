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
import ru.kalistratov.breathtraining2.controller.adapter.LevelSelectionAdapter
import ru.kalistratov.breathtraining2.controller.adapter.OnLevelSelectListener
import ru.kalistratov.breathtraining2.controller.adapter.TrainingSelectionAdapter
import ru.kalistratov.breathtraining2.model.training.ATraining
import ru.kalistratov.breathtraining2.model.training.plan.ATrainingPlan
import ru.kalistratov.breathtraining2.model.training.plan.TrainingPlans
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
        @Suppress("UNCHECKED_CAST") /* Always it`s TrainingPlan<Training>. */
        val trainingPlan = TrainingPlans.plans[planId] as ATrainingPlan<ATraining>

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

        val levelAdapter = LevelSelectionAdapter(LinkedList(trainingPlan.levels),this)
        levelList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        levelList.adapter = levelAdapter

        levelAdapter.onLevelSelectListener = OnLevelSelectListener {
            val levelTopic = findViewById<TextView>(R.id.levelTopic)
            val levelNum = getString(R.string.level) + " ${it.number}"
            levelTopic.text = levelNum
            trainingList.adapter = TrainingSelectionAdapter(LinkedList(it.trainings), baseContext)
        }

        trainingList.layoutManager = LinearLayoutManager(this)
        trainingList.adapter = TrainingSelectionAdapter(LinkedList(trainingPlan.levels[0].trainings), baseContext)
    }
}