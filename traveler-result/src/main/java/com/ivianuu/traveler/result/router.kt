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

package com.ivianuu.traveler.result

import com.ivianuu.traveler.Router
import com.ivianuu.traveler.goBack
import com.ivianuu.traveler.popTo
import com.ivianuu.traveler.popToRoot

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

/**
 * Goes back to the [key] and sends the [result] with the [resultCode]
 */
fun Router.popToWithResult(key: Any, resultCode: Int, result: Any) {
    popTo(key)
    sendResult(resultCode, result)
}

/**
 * Goes back to to the root and sends the [result] with the [resultCode]
 */
fun Router.popToRootWithResult(key: Any, resultCode: Int, result: Any) {
    popToRoot()
    sendResult(resultCode, result)
}