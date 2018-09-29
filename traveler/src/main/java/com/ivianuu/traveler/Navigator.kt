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

package com.ivianuu.traveler

/**
 * The navigator executes the [Command]'s
 */
interface Navigator {
    /**
     * Applies the [command]
     */
    fun applyCommand(command: Command)
}

/**
 * Returns a new [Navigator] which uses [applyCommand]
 */
fun Navigator(block: (Command) -> Unit) = object : Navigator {
    override fun applyCommand(command: Command) {
        block.invoke(command)
    }
}