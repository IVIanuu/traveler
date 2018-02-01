package com.ivianuu.traveler.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ivianuu.traveler.Traveler

class MainActivity : AppCompatActivity() {

    val traveler by lazy { Traveler.create() }
    private val fragmentNavigator by lazy { MainNavigator(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            traveler.router.newRootScreen(CounterKey(1))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        traveler.navigatorHolder.setNavigator(fragmentNavigator)
    }

    override fun onPause() {
        traveler.navigatorHolder.removeNavigator()
        super.onPause()
    }
}
