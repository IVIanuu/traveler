package com.ivianuu.traveler.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ivianuu.traveler.android.AppNavigator
import com.ivianuu.traveler.sample.MainScreens.CONDUCTOR
import com.ivianuu.traveler.sample.MainScreens.FRAGMENTS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        object : AppNavigator(this) {

            override fun createActivityIntent(context: Context, key: Any, data: Any?): Intent? {
                return when(key as MainScreens) {
                    CONDUCTOR -> Intent(context, ConductorActivity::class.java)
                    FRAGMENTS -> Intent(context, FragmentsActivity::class.java)
                }
            }

        }
    }

    private val traveler get() = getTraveler("main")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conductor.setOnClickListener { traveler.router.navigateTo(CONDUCTOR) }
        fragments.setOnClickListener { traveler.router.navigateTo(FRAGMENTS) }
    }

    override fun onResume() {
        super.onResume()
        traveler.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        traveler.navigatorHolder.removeNavigator()
        super.onPause()
    }
}

enum class MainScreens {
    CONDUCTOR, FRAGMENTS
}