package com.ivianuu.traveler.extension

import com.ivianuu.traveler.ResultListener
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.command.Back
import com.ivianuu.traveler.command.BackTo
import com.ivianuu.traveler.command.Forward
import com.ivianuu.traveler.command.Replace
import com.ivianuu.traveler.internal.Results

fun Router.navigateTo(key: Any, data: Any? = null) {
    executeCommands(Forward(key, data))
}

fun Router.newScreenChain(key: Any, data: Any? = null) {
    executeCommands(
        BackTo(null),
        Forward(key, data)
    )
}

fun Router.newRootScreen(key: Any, data: Any? = null) {
    executeCommands(
        BackTo(null),
        Replace(key, data)
    )
}

fun Router.replaceScreen(key: Any, data: Any? = null) {
    executeCommands(Replace(key, data))
}

fun Router.backTo(key: Any) {
    executeCommands(BackTo(key))
}

fun Router.backToRoot() {
    executeCommands(BackTo(null))
}

fun Router.finishChain() {
    executeCommands(
        BackTo(null),
        Back
    )
}

fun Router.exit() {
    executeCommands(Back)
}

fun Router.addResultListener(resultCode: Int, listener: ResultListener) {
    Results.addResultListener(resultCode, listener)
}

@JvmName("addResultListenerTyped")
inline fun <reified T> Router.addResultListener(
    resultCode: Int,
    crossinline onResult: (T) -> Unit
): (Any) -> Unit {
    val listener: (Any) -> Unit = { onResult(it as T) }
    addResultListener(resultCode, listener)
    return listener
}

fun Router.removeResultListener(resultCode: Int, listener: ResultListener) {
    Results.removeResultListener(resultCode, listener)
}

fun Router.sendResult(resultCode: Int, result: Any) =
    Results.sendResult(resultCode, result)

fun Router.exitWithResult(resultCode: Int, result: Any) {
    exit()
    sendResult(resultCode, result)
}