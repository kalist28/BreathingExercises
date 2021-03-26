package ru.kalistratov.breathtraining2.view.training.engine

interface EngineActions {
    fun onProgress()
    fun onPassedSecond(timeLeft: Int)
    fun onStepName(name: String)
    fun onPause()
    fun onContinue()
    fun onStartEngine()
    fun onStopEngine()
}