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
 * Base router
 */
open class Router {

    internal val commandBuffer = CommandBuffer()

    private val navigationListeners = mutableSetOf<NavigationListener>()

    /**
     * Executes the [commands] via the navigator or waits until one is set
     */
    open fun executeCommands(vararg commands: Command) {
        commandBuffer.executeCommands(commands)
        navigationListeners.toList().forEach { it(commands) }
    }

    /**
     * Notifies the [listener] on each [executeCommands] call
     */
    open fun addNavigationListener(listener: NavigationListener) {
        navigationListeners.add(listener)
    }

    /**
     * Removes the [listener]
     */
    open fun removeNavigationListener(listener: NavigationListener) {
        navigationListeners.remove(listener)
    }
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

/**
 * Adds the listener which will be invoked when [sendResult] was called with [resultCode].
 */
fun Router.addResultListener(resultCode: Int, listener: ResultListener) {
    Results.addResultListener(resultCode, listener)
}

/**
 * Adds the listener which will be invoked when [sendResult] was called with [resultCode].
 */
@JvmName("addResultListenerTyped")
inline fun <reified T> Router.addResultListener(
    resultCode: Int,
    crossinline onResult: (T) -> Unit
): (Any) -> Unit {
    val listener: (Any) -> Unit = { onResult(it as T) }
    addResultListener(resultCode, listener)
    return listener
}

/**
 * Removes the [listener] with [resultCode]
 */
fun Router.removeResultListener(resultCode: Int, listener: ResultListener) {
    Results.removeResultListener(resultCode, listener)
}

/**
 * Sends the [result] with the [resultCode]
 */
fun Router.sendResult(resultCode: Int, result: Any) =
    Results.sendResult(resultCode, result)

/**
 * Goes back to the previous screen and sends the [result] with the [resultCode]
 */
fun Router.goBackWithResult(resultCode: Int, result: Any) {
    goBack()
    sendResult(resultCode, result)
}