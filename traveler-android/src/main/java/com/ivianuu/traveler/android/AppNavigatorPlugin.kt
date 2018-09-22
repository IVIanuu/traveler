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

package com.ivianuu.traveler.android

import android.content.Context
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.plugin.NavigatorPlugin

/**
 * Plugin for starting and replacing activities
 */
abstract class AppNavigatorPlugin(context: Context) : NavigatorPlugin,
    AppNavigatorHelper.Callback {

    private val activityNavigatorHelper = AppNavigatorHelper(this, context)

    override fun handles(command: Command) =
        command is Forward || command is Replace

    override fun apply(command: Command) {
        when (command) {
            is Forward -> {
                if (!activityNavigatorHelper.forward(command)) {
                    unknownScreen(command)
                }
            }
            is Replace -> {
                if (!activityNavigatorHelper.replace(command)) {
                    unknownScreen(command)
                }
            }
        }
    }

    /**
     * Will be called when a unknown screen was requested
     */
    protected open fun unknownScreen(command: Command) {
        throw IllegalArgumentException("unknown screen $command")
    }
}