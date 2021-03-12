package ru.kalistratov.breathtraining2.view.training

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import ru.kalistratov.breathtraining2.R

class TrainingActivity : AppCompatActivity(R.layout.activity_training)  {

    private var trainingId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<ImageButton>(R.id.backspace).setOnClickListener {
            onBackPressed()
        }

        trainingId = intent.getIntExtra("trainingId", -1)

        if (trainingId == -1) {
            finish()
            return
        }

        MobileAds.initialize(this)
    }


}