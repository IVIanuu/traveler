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

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ivianuu.traveler.Back
import com.ivianuu.traveler.BackTo
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.plugin.NavigatorPlugin

/**
 * A plugin for implementing [Fragment] navigation
 */
open class FragmentNavigatorPlugin(
    fragmentManager: FragmentManager,
    containerId: Int
) : NavigatorPlugin, FragmentNavigatorHelper.Callback {

    private val fragmentNavigatorHelper =
        FragmentNavigatorHelper(this, fragmentManager, containerId)

    override fun invoke(command: Command): Boolean {
        return when (command) {
            is Back -> {
                if (!fragmentNavigatorHelper.back(command)) {
                    exit()
                }
                true
            }
            is BackTo -> {
                if (!fragmentNavigatorHelper.backTo(command)) {
                    backToUnexisting(command.key!!)
                }
                true
            }
            is Forward -> {
                if (!fragmentNavigatorHelper.forward(command)) {
                    unknownScreen(command)
                }
                true
            }
            is Replace -> {
                if (!fragmentNavigatorHelper.replace(command)) {
                    unknownScreen(command)
                }
                true
            }
            else -> false
        }
    }

    /**
     * Will be called when a unknown screen was requested
     */
    protected open fun unknownScreen(command: Command) {
        throw IllegalArgumentException("unknown screen $command")
    }

    /**
     * Will be called when [backTo] was called with an unknown screen
     */
    protected open fun backToUnexisting(key: Any) {
        fragmentNavigatorHelper.backToRoot()
    }

    /**
     * Exits this screen chain
     */
    protected open fun exit() {
    }
}

/**
 * Returns a new [FragmentNavigatorPlugin]
 */
fun FragmentActivity.FragmentNavigatorPlugin(containerId: Int) =
    FragmentNavigatorPlugin(supportFragmentManager, containerId)

/**
 * Returns a new [FragmentNavigatorPlugin]
 */
fun Fragment.FragmentNavigatorPlugin(containerId: Int) =
    FragmentNavigatorPlugin(childFragmentManager, containerId)