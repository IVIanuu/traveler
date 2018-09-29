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
import kotlin.reflect.KClass

/**
 * A [Navigator] which only accepts [Command]'s of type [T]
 */
abstract class TypedNavigator<T : Command>(private val clazz: KClass<T>) : Navigator {

    final override fun applyCommand(command: Command) {
        if (clazz.java.isAssignableFrom(command.javaClass)) {
            @Suppress("UNCHECKED_CAST")
            applyTypedCommand(command as T)
        } else {
            unsupportedCommand(command)
        }
    }

    /**
     * Applies the [command]
     */
    protected abstract fun applyTypedCommand(command: T)

    /**
     * Will be called when the [command] is not a sub type of [T]
     */
    protected open fun unsupportedCommand(command: Command) {
        throw IllegalArgumentException("unsupported command $command")
    }
}

/**
 * Returns a new [TypedNavigator] which uses [applyTypedCommand]
 */
inline fun <reified T : Command> TypedNavigator(noinline block: (command: T) -> Unit) =
    TypedNavigator(T::class, block)

/**
 * Returns a new [TypedNavigator] which uses [applyTypedCommand]
 */
fun <T : Command> TypedNavigator(clazz: KClass<T>, block: (command: T) -> Unit) =
    object : TypedNavigator<T>(clazz) {
        override fun applyTypedCommand(command: T) {
            block.invoke(command)
        }
    }