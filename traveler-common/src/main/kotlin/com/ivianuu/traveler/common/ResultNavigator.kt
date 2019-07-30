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

package com.ivianuu.traveler.common

import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Navigator

/**
 * A [Navigator] which returns whether or not the command was handled
 */
abstract class ResultNavigator : Navigator {

    final override fun applyCommand(command: Command) {
        if (!applyCommandWithResult(command)) {
            unhandledCommand(command)
        }
    }

    /**
     * Applies the [command] and returns whether or not the [command] was handled
     */
    abstract fun applyCommandWithResult(command: Command): Boolean

    /**
     * Will be called when [applyCommandWithResult] returns [false]
     */
    protected open fun unhandledCommand(command: Command) {
        throw IllegalArgumentException("couldn't handle command $command")
    }
}