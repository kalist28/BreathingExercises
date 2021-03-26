package ru.kalistratov.breathtraining2.view.training.engine

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.kalistratov.breathtraining2.model.training.ATraining

class TrainingEngineService : Service() {

    inner class Binder : android.os.Binder() {
        fun service() : TrainingEngineService
        {
            return this@TrainingEngineService
        }
    }

    private lateinit var binder: Binder
    private lateinit var engine: TrainingEngine
    private lateinit var training: ATraining
    lateinit var engineActions: EngineActions

    fun init(training: ATraining, engineActions: EngineActions) {
        this.engineActions = engineActions
        this.training = training
        engine = TrainingEngine(engineActions, training)
        engine.startEngine()
    }

    override fun onCreate() {
        super.onCreate()
        binder = Binder()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}