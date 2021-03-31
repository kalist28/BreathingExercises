package ru.kalistratov.breathtraining2.view.training.engine

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.kalistratov.breathtraining2.model.training.Training


class TrainingEngineService : Service() {

    inner class Binder : android.os.Binder() {
        fun service() : TrainingEngineService
        {
            return this@TrainingEngineService
        }
    }

    private lateinit var binder: Binder
    private lateinit var training: Training

    lateinit var engine: TrainingEngine

    fun init(training: Training) {
        this.training = training
        engine = TrainingEngine(training, this)

    }
    public fun startEngine() {
        engine.startEngine()
    }

    override fun onCreate() {
        super.onCreate()
        binder = Binder()
    }

    override fun onDestroy() {
        super.onDestroy()
        engine.stopEngine()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}