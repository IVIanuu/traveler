package com.ivianuu.traveler

import com.ivianuu.traveler.internal.Results

/**
 * Navigates forward to [key]
 */
fun Router.navigateTo(key: Any, data: Any? = null) {
    executeCommands(Forward(key, data))
}

/**
 * Pops back to root and goes forward to [key]
 */
fun Router.newScreenChain(key: Any, data: Any? = null) {
    executeCommands(
        BackTo(null),
        Forward(key, data)
    )
}

/**
 * Clears all screen and opens [key]
 */
fun Router.newRootScreen(key: Any, data: Any? = null) {
    executeCommands(
        BackTo(null),
        Replace(key, data)
    )
}

/**
 * Replaces the top screen with [key]
 */
fun Router.replaceScreen(key: Any, data: Any? = null) {
    executeCommands(Replace(key, data))
}

/**
 * Goes back to [key]
 */
fun Router.backTo(key: Any) {
    executeCommands(BackTo(key))
}

/**
 * Goes back to the root screen
 */
fun Router.backToRoot() {
    executeCommands(BackTo(null))
}

/**
 * Finishes the chain
 */
fun Router.finishChain() {
    executeCommands(
        BackTo(null),
        Back
    )
}

/**
 * Goes back to the previous screen
 */
fun Router.exit() {
    executeCommands(Back)
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
fun Router.exitWithResult(resultCode: Int, result: Any) {
    exit()
    sendResult(resultCode, result)
}