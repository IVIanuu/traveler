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
     * Returns whether or not the [command] can be handled
     */
    fun handles(command: Command): Boolean

    /**
     * Applies the [command] this will be only called if [handles] returns true
     */
    fun apply(command: Command)
}

/**
 * A typed [NavigatorPlugin]
 */
abstract class TypedNavigatorPlugin<T : Command>(private val clazz: KClass<T>) : NavigatorPlugin {
    override fun handles(command: Command) = clazz.java.isAssignableFrom(command.javaClass)
    final override fun apply(command: Command) {
        applyTyped(clazz.java.cast(command)!!)
    }

    abstract fun applyTyped(command: T)
}