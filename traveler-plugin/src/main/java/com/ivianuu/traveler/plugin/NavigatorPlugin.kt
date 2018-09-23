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

package com.ivianuu.traveler.plugin

import com.ivianuu.traveler.Command
import kotlin.reflect.KClass

/**
 * A single component which is able to handle specific [Command]'s
 * Returns whether or not the [Command] was handled
 */
typealias NavigatorPlugin = (command: Command) -> Boolean

/**
 * A typed [NavigatorPlugin]
 */
inline fun <reified T : Command> NavigatorPlugin(noinline block: (command: T) -> Boolean) =
    NavigatorPlugin(T::class, block)

/**
 * A typed [NavigatorPlugin]
 */
fun <T : Command> NavigatorPlugin(
    clazz: KClass<T>,
    block: (command: T) -> Boolean
): NavigatorPlugin = {
    if (clazz.java.isAssignableFrom(it.javaClass)) {
        @Suppress("UNCHECKED_CAST")
        block.invoke(it as T)
    } else {
        false
    }
}