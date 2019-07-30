package com.ivianuu.traveler.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.traveler.android.AppNavigator
import com.ivianuu.traveler.android.setNavigator
import com.ivianuu.traveler.push
import com.ivianuu.traveler.sample.fragment.FragmentsKey
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val router by lazy { router("main") }
    private val navigator by lazy(LazyThreadSafetyMode.NONE) { AppNavigator(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.setOnClickListener { router.push(FragmentsKey()) }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        router.setNavigator(this, navigator)
    }
}