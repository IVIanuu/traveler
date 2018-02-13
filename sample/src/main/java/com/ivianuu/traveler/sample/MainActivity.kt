package com.ivianuu.traveler.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ivianuu.traveler.Traveler
import com.ivianuu.traveler.keys.ActivityKey
import com.ivianuu.traveler.keys.KeyNavigator

object MainKey : ActivityKey() {
    override fun createIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }
}

class MainActivity : AppCompatActivity() {

    val traveler by lazy { Traveler.create() }
    private val fragmentNavigator by lazy {
        KeyNavigator(this, supportFragmentManager, R.id.fragment_container) }

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
