package com.ivianuu.traveler.extension

import com.ivianuu.traveler.NavigationListener
import com.ivianuu.traveler.ResultListener
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.commands.Command

fun Router.addResultListener(resultCode: Int, onResult: (Any) -> Unit): ResultListener {
    val listener = object : ResultListener {
        override fun onResult(result: Any) {
            onResult.invoke(result)
        }
    }

    addResultListener(resultCode, listener)

    return listener
}

fun Router.addNavigationListener(onApplyCommands: (Array<out Command>) -> Unit): NavigationListener {
    val listener = object : NavigationListener {
        override fun onApplyCommands(commands: Array<out Command>) {
            onApplyCommands.invoke(commands)
        }
    }

    addNavigationListener(listener)

    return listener
}