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
import kotlin.reflect.KClass

/**
 * A [ResultNavigator] which only accepts [Command]'s of type [T]
 */
abstract class TypedResultNavigator<T : Command>(private val clazz: KClass<T>) : ResultNavigator() {
    final override fun applyCommandWithResult(command: Command): Boolean {
        return if (clazz.java.isAssignableFrom(command.javaClass)) {
            @Suppress("UNCHECKED_CAST")
            applyTypedCommandWithResult(command as T)
        } else {
            false
        }
    }

    /**
     * Returns whether or not the [command] was handled
     */
    protected abstract fun applyTypedCommandWithResult(command: T): Boolean
}

/**
 * Returns a new [TypedResultNavigator] which uses [block]
 */
inline fun <reified T : Command> TypedResultNavigator(noinline block: (command: T) -> Boolean) =
    TypedResultNavigator(T::class, block)

/**
 * Returns a new [TypedResultNavigator] which uses [block]
 */
fun <T : Command> TypedResultNavigator(clazz: KClass<T>, block: (command: T) -> Boolean) =
    object : TypedResultNavigator<T>(clazz) {
        override fun applyTypedCommandWithResult(command: T) = block.invoke(command)
    }