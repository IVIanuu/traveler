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
import com.ivianuu.traveler.result.ResultListener

/**
 * Router
 */
open class Router : BaseRouter() {

    private val resultListeners = mutableMapOf<Int, ResultListener>()

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

    open fun backTo(key: Any) {
        executeCommands(BackTo(key))
    }

    open fun backToRoot() {
        executeCommands(BackTo(null))
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
        resultListeners[resultCode] = listener
    }

    open fun removeResultListener(resultCode: Int) {
        resultListeners.remove(resultCode)
    }

    open fun sendResult(resultCode: Int, result: Any): Boolean {
        val resultListener = resultListeners[resultCode]
        if (resultListener != null) {
            resultListener.onResult(result)
            return true
        }

        return false
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

    @JvmOverloads
    open fun exitWithMessage(messageRes: Int, vararg args: Any = emptyArray()) {
        executeCommands(
            Back(),
            SystemMessageRes(messageRes, *args)
        )
    }

    open fun showSystemMessage(message: String) {
        executeCommands(SystemMessage(message))
    }

    @JvmOverloads
    open fun showSystemMessage(messageRes: Int, vararg args: Any = emptyArray()) {
        executeCommands(SystemMessageRes(messageRes, *args))
    }
}