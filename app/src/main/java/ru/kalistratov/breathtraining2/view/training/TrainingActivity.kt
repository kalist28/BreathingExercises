package ru.kalistratov.breathtraining2.view.training

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.model.training.ATraining
import ru.kalistratov.breathtraining2.model.training.TriangleTraining
import ru.kalistratov.breathtraining2.model.training.plan.TrainingPlans
import ru.kalistratov.breathtraining2.view.training.engine.EngineActions
import ru.kalistratov.breathtraining2.view.training.engine.TrainingEngineService
import java.util.*

class TrainingActivity : AppCompatActivity(R.layout.activity_training)  {

    companion object {
        const val EN_PLAN_ID         = "planId"
        const val EN_LEVEL_NUMBER    = "levelNumber"
        const val EN_TRAINING_NUMBER = "trainingNumber"
    }

    private var planId: Byte = -1
    private var levelNumber: Byte = -1
    private var trainingNumber: Byte = -1
    private var training: ATraining? = null
    private lateinit var mService: TrainingEngineService
    private var mBound: Boolean? = null

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            val binder = service as TrainingEngineService.Binder
            mService = binder.service()
            mBound = true

            val engineActions = object : EngineActions {
                override fun onProgress() {

                    TODO("Реализовать метод")

                }

                override fun onPassedSecond(timeLeft: Int) {
                    findViewById<TextView>(R.id.time).text = timeLeft.toString()
                }

                override fun onStepName(name: String) {
                    findViewById<TextView>(R.id.topic).text = name.toUpperCase(Locale.ROOT)
                }

                override fun onPause() {
                    TODO("Реализовать метод")
                }

                override fun onContinue() {

                    TODO("Реализовать метод")
                }

                override fun onStartEngine() {

                    TODO("Реализовать метод")
                }

                override fun onStopEngine() {

                    TODO("Реализовать метод")

                }

            }

            training?.let { mService.init(it, engineActions) }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<ImageButton>(R.id.backspace).setOnClickListener {
            onBackPressed()
        }

        planId = intent.getByteExtra(EN_PLAN_ID, -1)
        levelNumber = intent.getByteExtra(EN_LEVEL_NUMBER, -1)
        trainingNumber = intent.getByteExtra(EN_TRAINING_NUMBER, -1)

        val error = (-1).toByte()
        if (planId == error || levelNumber == error || trainingNumber == error) {
            finish()
            return
        }

        training = TrainingPlans.findTrainingInPlan(planId, levelNumber, trainingNumber)

        Log.e("TAGEEEE", "onCreate: $training " + (training is TriangleTraining) )

        val a = TrainingEngineService()
        Intent(this, a::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
        }


    }

    override fun onStop() {
        super.onStop()
        //unbindService(connection)
        //mBound = false
    }


}