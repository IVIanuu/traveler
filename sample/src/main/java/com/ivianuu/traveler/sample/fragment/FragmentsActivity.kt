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
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ivianuu.traveler.fragment.FragmentNavigatorPlugin
import com.ivianuu.traveler.lifecycle.setNavigator
import com.ivianuu.traveler.plugin.pluginNavigatorOf
import com.ivianuu.traveler.sample.getTraveler
import com.ivianuu.traveler.sample.widget.CounterKey
import com.ivianuu.traveler.setRoot

private class SampleFragmentNavigatorPlugin(
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

    private val traveler get() = getTraveler("fragments")

    private val fragmentNavigator by lazy(LazyThreadSafetyMode.NONE) {
        pluginNavigatorOf(
            SampleFragmentNavigatorPlugin(
                this,
                supportFragmentManager,
                android.R.id.content
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            AsyncTask.execute { traveler.router.setRoot(CounterKey(1)) }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        traveler.navigatorHolder.setNavigator(this, fragmentNavigator)
    }
}