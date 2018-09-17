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

import com.ivianuu.traveler.command.Back
import com.ivianuu.traveler.command.BackTo
import com.ivianuu.traveler.command.Command
import com.ivianuu.traveler.command.Forward
import com.ivianuu.traveler.command.Replace

/**
 * Router
 */
open class Router : BaseRouter() {

    private val navigationListeners = mutableSetOf<(Array<out Command>) -> Unit>()
    private val resultListeners = mutableMapOf<Int, MutableSet<((Any) -> Unit)>>()

    open fun navigateTo(key: Any, data: Any? = null) {
        executeCommands(Forward(key, data))
    }

    open fun newScreenChain(key: Any, data: Any? = null) {
        executeCommands(
            BackTo(null),
            Forward(key, data)
        )
    }

    open fun newRootScreen(key: Any, data: Any? = null) {
        executeCommands(
            BackTo(null),
            Replace(key, data)
        )
    }

    open fun replaceScreen(key: Any, data: Any? = null) {
        executeCommands(Replace(key, data))
    }

    open fun backTo(key: Any) {
        executeCommands(BackTo(key))
    }

    open fun backToRoot() {
        executeCommands(BackTo(null))
    }

    open fun finishChain() {
        executeCommands(
            BackTo(null),
            Back
        )
    }

    open fun exit() {
        executeCommands(Back)
    }

    fun addResultListener(resultCode: Int, listener: (Any) -> Unit) {
        val listeners = resultListeners.getOrPut(resultCode) { mutableSetOf() }
        listeners.add(listener)
    }

    fun removeResultListener(resultCode: Int, listener: (Any) -> Unit) {
        val listeners = resultListeners[resultCode] ?: return
        listeners.remove(listener)
        if (listeners.isEmpty()) {
            resultListeners.remove(resultCode)
        }
    }

    open fun sendResult(resultCode: Int, result: Any): Boolean {
        val listeners = resultListeners[resultCode]?.toList()
        if (listeners != null) {
            listeners.forEach { it(result) }
            return true
        }

        return false
    }

    open fun exitWithResult(resultCode: Int, result: Any) {
        exit()
        sendResult(resultCode, result)
    }

    fun addNavigationListener(listener: (Array<out Command>) -> Unit) {
        navigationListeners.add(listener)
    }

    fun removeNavigationListener(listener: (Array<out Command>) -> Unit) {
        navigationListeners.remove(listener)
    }

    override fun executeCommands(vararg commands: Command) {
        super.executeCommands(*commands)
        navigationListeners.forEach { it(commands) }
    }
}