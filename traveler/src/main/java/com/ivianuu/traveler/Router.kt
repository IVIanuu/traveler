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

import android.annotation.SuppressLint
import com.ivianuu.traveler.commands.*
import com.ivianuu.traveler.result.ResultListener
import java.util.*

/**
 * Router
 */
open class Router : BaseRouter() {

    private val resultListeners = mutableListOf<Pair<Int, ResultListener>>()

    open fun navigateTo(key: Any) {
        executeCommands(Forward(key))
    }

    open fun newScreenChain(key: Any) {
        executeCommands(
            BackTo(null),
            Forward(key)
        )
    }

    open fun newRootScreen(key: Any) {
        executeCommands(
            BackTo(null),
            Replace(key)
        )
    }

    open fun replaceScreen(key: Any) {
        executeCommands(Replace(key))
    }

    @JvmOverloads
    open fun backTo(key: Any? = null) {
        executeCommands(BackTo(key))
    }

    open fun finishChain() {
        executeCommands(
            BackTo(null),
            Back()
        )
    }

    open fun exit() {
        executeCommands(Back())
    }

    open fun setResultListener(resultCode: Int, listener: ResultListener) {
        resultListeners.add(resultCode to listener)
    }

    open fun removeResultListener(listener: ResultListener) {
        val index = resultListeners.indexOfFirst { it.second == listener }
        if (index != -1) resultListeners.removeAt(index)
    }

    open fun sendResult(resultCode: Int, result: Any): Boolean {
        var notified = false
        resultListeners
            .filter { it.first == resultCode }
            .map { it.second }
            .forEach {
                it.onResult(result)
                notified = true
            }

        return notified
    }

    open fun exitWithResult(resultCode: Int, result: Any) {
        exit()
        sendResult(resultCode, result)
    }

    open fun exitWithMessage(message: String) {
        executeCommands(
            Back(),
            SystemMessage(message)
        )
    }

    open fun showSystemMessage(message: String) {
        executeCommands(SystemMessage(message))
    }
}