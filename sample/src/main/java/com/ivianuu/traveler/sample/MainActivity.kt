package com.ivianuu.traveler.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.traveler.android.ActivityKey
import com.ivianuu.traveler.android.AppNavigatorPlugin
import com.ivianuu.traveler.lifecycle.setNavigator
import com.ivianuu.traveler.navigate
import com.ivianuu.traveler.plugin.pluginNavigatorOf
import com.ivianuu.traveler.sample.director.DirectorActivity
import com.ivianuu.traveler.sample.fragment.FragmentsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val traveler by lazy { traveler("main") }
    private val navigatorHolder get() = traveler.navigatorHolder
    private val router get() = traveler.router

    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        pluginNavigatorOf(AppNavigatorPlugin())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.setOnClickListener { router.navigate(ActivityKey<FragmentsActivity>()) }
        director.setOnClickListener { router.navigate(ActivityKey<DirectorActivity>()) }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(this, navigator)
    }
}