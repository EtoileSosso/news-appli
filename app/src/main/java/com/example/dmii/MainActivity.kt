package com.example.dmii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.dmii.fragments.ChoiceFragment
import com.example.dmii.fragments.LocationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToChoice.setOnClickListener {
            val fragment = ChoiceFragment()
            openFragment(fragment)
        }

        goToLocation.setOnClickListener {
            val fragment = LocationFragment()
            openFragment(fragment)
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(null)
        }.commit()
    }
}