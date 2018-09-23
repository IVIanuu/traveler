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
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.android.AppNavigatorHelper

/**
 * An plugin for fragments and activities
 */
open class FragmentAppNavigatorPlugin(
    private val context: Context,
    fragmentManager: FragmentManager,
    containerId: Int
) : FragmentNavigatorPlugin(fragmentManager, containerId), AppNavigatorHelper.Callback {

    private val activityNavigatorHelper = AppNavigatorHelper(this, context)

    override fun invoke(command: Command): Boolean {
        return when (command) {
            is Forward -> {
                if (!activityNavigatorHelper.forward(command)) {
                    super.invoke(command)
                }
                true
            }
            is Replace -> {
                if (!activityNavigatorHelper.replace(command)) {
                    super.invoke(command)
                }
                true
            }
            else -> super.invoke(command)
        }
    }

    override fun exit() {
        (context as? Activity ?: throw IllegalStateException("context must be an activity"))
            .finish()
    }
}

/**
 * Returns a new [FragmentAppNavigatorPlugin]
 */
fun FragmentActivity.FragmentAppNavigatorPlugin(containerId: Int) =
    FragmentAppNavigatorPlugin(this, supportFragmentManager, containerId)

/**
 * Returns a new [FragmentAppNavigatorPlugin]
 */
fun Fragment.FragmentAppNavigatorPlugin(containerId: Int) =
    FragmentAppNavigatorPlugin(requireContext(), childFragmentManager, containerId)