package ru.kalistratov.breathtraining2.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.model.training.plan.TrainingPlans


class MainActivity : AppCompatActivity() {
    private lateinit var navigationBar: ChipNavigationBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TrainingPlans.init(this)

        setContentView(R.layout.activity_main)
        navigationBar = findViewById(R.id.navigationBar)
        navigationBar.setOnItemSelectedListener {
            when(it) {
                R.id.home -> loadFragment(PlanSelectionFragment())
                //R.id.details -> loadFragment(DetailsFragment())
                R.id.person -> loadFragment(PersonFragment())
                R.id.settings -> loadFragment(SettingsFragment())
            }
        }

        navigationBar.setItemSelected(R.id.home)

    }

    private fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_content, fragment)
        ft.commit()
    }

}