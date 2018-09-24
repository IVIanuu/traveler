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
 */
interface NavigatorPlugin {
    /**
     * Returns whether the [command] was handled or not
     */
    fun applyCommand(command: Command): Boolean
}

fun NavigatorPlugin(block: (command: Command) -> Boolean) = object : NavigatorPlugin {
    override fun applyCommand(command: Command) = block.invoke(command)
}

/**
 * A typed [NavigatorPlugin]
 */
abstract class TypedNavigatorPlugin<T : Command>(private val clazz: KClass<T>) : NavigatorPlugin {
    override fun applyCommand(command: Command) =
        if (clazz.java.isAssignableFrom(command.javaClass)) {
            applyCommandTyped(clazz.java.cast(command)!!)
            true
        } else {
            false
        }

    /**
     * Returns whether the [command] was handled or not
     */
    protected abstract fun applyCommandTyped(command: T): Boolean
}

/**
 * A typed [NavigatorPlugin]
 */
@JvmName("NavigatorPluginTyped")
inline fun <reified T : Command> NavigatorPlugin(noinline block: (command: T) -> Boolean) =
    NavigatorPlugin(T::class, block)

/**
 * A typed [NavigatorPlugin]
 */
fun <T : Command> NavigatorPlugin(
    clazz: KClass<T>,
    block: (command: T) -> Boolean
) = object : TypedNavigatorPlugin<T>(clazz) {
    override fun applyCommandTyped(command: T) = block.invoke(command)
}