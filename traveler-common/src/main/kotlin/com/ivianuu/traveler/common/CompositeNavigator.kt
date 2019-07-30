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
 * A [Navigator] which wraps a couple of [ResultNavigator]'s and dispatches all commands
 * To them
 */
class CompositeNavigator(private val navigators: List<ResultNavigator>) : ResultNavigator() {

    init {
        if (navigators.isEmpty()) {
            throw IllegalArgumentException("atleast 1 navigator must be added")
        }
    }

    override fun applyCommandWithResult(command: Command) =
        navigators.any { it.applyCommandWithResult(command) }
}

/**
 * Returns a new [CompositeNavigator] with [navigators]
 */
fun compositeNavigatorOf(navigators: Collection<ResultNavigator>) =
    CompositeNavigator(navigators.toList())

/**
 * Returns a new [CompositeNavigator] with [navigators]
 */
fun compositeNavigatorOf(vararg navigators: ResultNavigator) =
    CompositeNavigator(navigators.toList())