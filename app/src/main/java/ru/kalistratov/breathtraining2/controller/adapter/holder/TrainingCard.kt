package ru.kalistratov.breathtraining2.controller.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import ru.kalistratov.breathtraining2.R
import java.util.*

open class TrainingCard(val view: View) : DoubleStatusView(view) {

    val card: CardView = view.findViewById(R.id.card)
    val topic: TextView = view.findViewById(R.id.inhale)

    fun isAdBanner() {
        deactivateAllViews()
        val viewDeactivate: View = view.findViewById(R.id.add_banner)
        viewDeactivate.visibility = View.VISIBLE

        val mAdView : AdView = viewDeactivate.findViewById(R.id.adView)
        MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("F83FCB791B4AB587E0D0AAD7E66C34BC")).build())
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

}