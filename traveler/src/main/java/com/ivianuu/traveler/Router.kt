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
 * Router
 */
open class Router {

    internal val commandBuffer = CommandBuffer()

    private val navigationListeners = mutableSetOf<NavigationListener>()

    /**
     * Sends the [commands] to the [Navigator]
     */
    open fun executeCommands(vararg commands: Command) {
        commandBuffer.executeCommands(commands)

        val listeners = navigationListeners.toList()
        commands.forEach { command ->
            listeners.forEach { it(command) }
        }
    }

    /**
     * Notifies the [listener] on each [executeCommands] call
     */
    fun addNavigationListener(listener: NavigationListener) {
        navigationListeners.add(listener)
    }

    /**
     * Removes the [listener]
     */
    fun removeNavigationListener(listener: NavigationListener) {
        navigationListeners.remove(listener)
    }
}

/**
 * Sends the [commands] to the [Navigator]
 */
fun Router.executeCommands(commands: Collection<Command>) {
    executeCommands(*commands.toTypedArray())
}

/**
 * Navigates forward to [key]
 */
fun Router.navigate(key: Any, data: Any? = null) {
    executeCommands(Forward(key, data))
}

/**
 * Clears all screen and opens [key]
 */
fun Router.setRoot(key: Any, data: Any? = null) {
    executeCommands(
        BackTo(null),
        Replace(key, data)
    )
}

/**
 * Replaces the top screen with [key]
 */
fun Router.replaceTop(key: Any, data: Any? = null) {
    executeCommands(Replace(key, data))
}

/**
 * Opens all [keys]
 */
fun Router.newChain(vararg keys: Any) {
    val commands =
        arrayOf(BackTo(null), *keys.map { Forward(it, null) }.toTypedArray())
    executeCommands(*commands)
}

/**
 * Pops to the root and opens all [keys]
 */
fun Router.newRootChain(vararg keys: Any) {
    val commands =
        arrayOf(BackTo(null), *keys.map { Forward(it, null) }.toTypedArray())
    executeCommands(*commands)
}

/**
 * Goes back to [key]
 */
fun Router.popTo(key: Any) {
    executeCommands(BackTo(key))
}

/**
 * Goes back to the root screen
 */
fun Router.popToRoot() {
    executeCommands(BackTo(null))
}

/**
 * Goes back to the previous screen
 */
fun Router.goBack() {
    executeCommands(Back)
}

/**
 * Finishes the chain
 */
fun Router.finish() {
    executeCommands(
        BackTo(null),
        Back
    )
}