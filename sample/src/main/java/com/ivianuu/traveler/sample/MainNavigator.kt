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

package com.ivianuu.traveler.sample

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.widget.Toast
import com.ivianuu.traveler.supportfragments.SupportFragmentNavigator

/**
 * @author Manuel Wrage (IVIanuu)
 */
class MainNavigator(
    private val activity: MainActivity
) : SupportFragmentNavigator(activity.supportFragmentManager, R.id.fragment_container) {

    override fun createFragment(key: Any): Fragment? {
        return CounterFragment().apply {
            arguments = Bundle().apply {
                putParcelable("key", key as Parcelable)
            }
        }
    }

    override fun getFragmentTag(key: Any): String {
        return "count_" + (key as CounterKey).count
    }

    override fun showSystemMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun exit() {
        activity.finish()
    }


}