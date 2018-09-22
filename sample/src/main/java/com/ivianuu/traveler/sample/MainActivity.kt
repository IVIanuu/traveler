package com.ivianuu.traveler.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.traveler.android.ActivityPlugin
import com.ivianuu.traveler.lifecycle.setNavigator
import com.ivianuu.traveler.navigate
import com.ivianuu.traveler.plugin.pluginNavigatorOf
import com.ivianuu.traveler.sample.MainScreens.FRAGMENTS
import com.ivianuu.traveler.sample.fragment.FragmentsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        pluginNavigatorOf(
            object : ActivityPlugin(this) {
                override fun createActivityIntent(context: Context, key: Any, data: Any?): Intent? {
                    return when (key as MainScreens) {
                        FRAGMENTS -> Intent(context, FragmentsActivity::class.java)
                    }
                }
            },
            ToastPlugin(this)
        )
    }

    private val traveler get() = getTraveler("main")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.setOnClickListener { traveler.router.navigate(FRAGMENTS) }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        traveler.navigatorHolder.setNavigator(this, navigator)
    }
}

enum class MainScreens {
    FRAGMENTS
}