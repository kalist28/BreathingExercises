package ru.kalistratov.breathtraining2.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.controller.UserPreferences
import ru.kalistratov.breathtraining2.model.training.plan.TrainingPlans

/**
 * The main activity of app.
 *
 * On this a menu for choosing between [PlanSelectionFragment],
 * [PersonFragment] and [SettingsFragment].
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navigationBar: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TrainingPlans.init(this)
        UserPreferences.init(this)

        navigationBar = findViewById(R.id.navigationBar)
        navigationBar.setOnItemSelectedListener {
            when (it) {
                R.id.home -> loadFragment(PlanSelectionFragment())
                //R.id.details -> loadFragment(DetailsFragment())
                R.id.person -> loadFragment(PersonFragment())
                R.id.settings -> loadFragment(SettingsFragment())
            }
        }

        navigationBar.setItemSelected(R.id.home)
    }

    /**
     * Load fragment on frame.
     *
     * @param fragment the fragment which need load on frame.
     */
    private fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_content, fragment)
        ft.commit()
    }

}