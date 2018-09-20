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

package com.ivianuu.traveler.fragment

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.android.AppNavigatorHelper

/**
 * Navigator for fragments and activities
 */
abstract class FragmentAppNavigator(
    private val activity: Activity,
    fragmentManager: FragmentManager,
    containerId: Int
) : FragmentNavigator(fragmentManager, containerId), AppNavigatorHelper.Callback {

    private val appNavigatorHelper = AppNavigatorHelper(this, activity)

    override fun forward(command: Forward) {
        if (!appNavigatorHelper.forward(command)) {
            super.forward(command)
        }
    }

    override fun replace(command: Replace) {
        if (!appNavigatorHelper.replace(command)) {
            super.replace(command)
        }
    }

    override fun exit() {
        activity.finish()
    }

}