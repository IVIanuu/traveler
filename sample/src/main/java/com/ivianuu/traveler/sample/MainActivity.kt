package com.ivianuu.traveler.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ivianuu.traveler.Traveler
import com.ivianuu.traveler.keys.ActivityClassKey
import com.ivianuu.traveler.keys.KeyNavigator

object MainKey : ActivityClassKey(MainActivity::class)

class MainActivity : AppCompatActivity() {

    val traveler = Traveler.create()
    private val fragmentNavigator by lazy(LazyThreadSafetyMode.NONE) {
        KeyNavigator(this, supportFragmentManager, android.R.id.content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
