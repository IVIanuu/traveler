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
 * The object used to navigate around
 */
interface Router {

    /**
     * Whether or not a [Navigator] is currently set
     */
    val hasNavigator: Boolean

    /**
     * Sets the [navigator] which will be used to navigate
     */
    fun setNavigator(navigator: Navigator)

    /**
     * Removes the current [Navigator]
     */
    fun removeNavigator()

    /**
     * Sends the [commands] to the [Navigator]
     */
    fun enqueueCommands(vararg commands: Command)

    /**
     * Adds the [listener]
     */
    fun addRouterListener(listener: RouterListener)

    /**
     * Removes the previously added [listener]
     */
    fun removeRouterListener(listener: RouterListener)

}

/**
 * Returns a new [Router] instance
 */
fun Router(): Router = RealRouter()

/**
 * Sends the [commands] to the [Navigator]
 */
fun Router.enqueueCommands(commands: Collection<Command>) {
    enqueueCommands(*commands.toTypedArray())
}

/**
 * Navigates forward to [key]
 */
fun Router.navigate(key: Any, data: Any? = null) {
    enqueueCommands(Forward(key, data))
}

/**
 * Clears all screen and opens [key]
 */
fun Router.setRoot(key: Any, data: Any? = null) {
    enqueueCommands(
        BackTo(null),
        Replace(key, data)
    )
}

/**
 * Replaces the top screen with [key]
 */
fun Router.replaceTop(key: Any, data: Any? = null) {
    enqueueCommands(Replace(key, data))
}

/**
 * Opens all [keys]
 */
fun Router.newChain(vararg keys: Any) {
    val commands =
        arrayOf(BackTo(null), *keys.map { Forward(it, null) }.toTypedArray())
    enqueueCommands(*commands)
}

/**
 * Pops to the root and opens all [keys]
 */
fun Router.newRootChain(vararg keys: Any) {
    val commands =
        arrayOf(BackTo(null), *keys.map { Forward(it, null) }.toTypedArray())
    enqueueCommands(*commands)
}

/**
 * Goes back to [key]
 */
fun Router.popTo(key: Any) {
    enqueueCommands(BackTo(key))
}

/**
 * Goes back to the root screen
 */
fun Router.popToRoot() {
    enqueueCommands(BackTo(null))
}

/**
 * Goes back to the previous screen
 */
fun Router.goBack() {
    enqueueCommands(Back())
}

/**
 * Finishes the chain
 */
fun Router.finish() {
    enqueueCommands(
        BackTo(null),
        Back()
    )
}