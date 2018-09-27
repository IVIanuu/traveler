/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.traveler.sample.fragment

import android.app.Activity
import android.os.Bundle
import com.ivianuu.traveler.Traveler
import com.ivianuu.traveler.fragment.FragmentNavigatorPlugin
import com.ivianuu.traveler.goBack
import com.ivianuu.traveler.lifecycle.setNavigator
import com.ivianuu.traveler.plugin.pluginNavigatorOf
import com.ivianuu.traveler.sample.ToastPlugin
import com.ivianuu.traveler.setRoot

private class CounterNavigatorPlugin(
    private val activity: Activity,
    fragmentManager: FragmentManager,
    containerId: Int
) : FragmentNavigatorPlugin(fragmentManager, containerId) {
    override fun createFragment(key: Any, data: Any?): Fragment? {
        return CounterFragment().apply {
            arguments = Bundle().apply {
                putParcelable("key", key as CounterKey)
            }
        }
    }

    override fun exit() {
        activity.finish()
    }
}

class FragmentsActivity : AppCompatActivity() {

    private val traveler = Traveler()
    private val navigatorHolder = traveler.navigatorHolder
    private val router = traveler.router

    private val fragmentNavigator by lazy(LazyThreadSafetyMode.NONE) {
        pluginNavigatorOf(
            CounterNavigatorPlugin(
                this,
                supportFragmentManager,
                android.R.id.content
            ),
            ToastPlugin(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            router.setRoot(CounterKey(1))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(this, fragmentNavigator)
    }

    override fun onBackPressed() {
        router.goBack()
    }
}