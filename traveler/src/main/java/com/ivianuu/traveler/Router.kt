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

import com.ivianuu.traveler.commands.*

/**
 * Router
 */
open class Router : BaseRouter() {

    private val resultListeners = mutableMapOf<Int, MutableSet<ResultListener>>()

    open fun navigateTo(key: Any, data: Any? = null) {
        executeCommands(Forward(key, data))
    }

    open fun newChain(key: Any, data: Any? = null) {
        executeCommands(
            BackTo(null),
            Forward(key, data)
        )
    }

    open fun newRoot(key: Any, data: Any? = null) {
        executeCommands(
            BackTo(null),
            Replace(key, data)
        )
    }

    open fun replaceTop(key: Any, data: Any? = null) {
        executeCommands(Replace(key, data))
    }

    open fun pop() {
        executeCommands(Back)
    }

    open fun popTo(key: Any) {
        executeCommands(BackTo(key))
    }

    open fun popToRoot() {
        executeCommands(BackTo(null))
    }

    open fun finish() {
        executeCommands(
            BackTo(null),
            Back
        )
    }

    open fun custom(command: Command) {
        executeCommand(command)
    }

    open fun addResultListener(resultCode: Int, listener: ResultListener) {
        val listeners = resultListeners.getOrPut(resultCode) { mutableSetOf() }
        listeners.add(listener)
    }

    open fun removeResultListener(resultCode: Int, listener: ResultListener) {
        resultListeners[resultCode]?.remove(listener)
    }

    open fun sendResult(resultCode: Int, result: Any): Boolean {
        val listeners = resultListeners[resultCode]?.toList()
        if (listeners != null) {
            listeners.forEach { it.onResult(result) }
            return true
        }

        return false
    }

    open fun popWithResult(resultCode: Int, result: Any) {
        pop()
        sendResult(resultCode, result)
    }
}