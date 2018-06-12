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

import com.ivianuu.traveler.commands.Command

fun NavigatorHolder.setNavigator(applyCommands: (commands: Array<Command>) -> Unit): Navigator {
    val navigator = object : Navigator {
        override fun applyCommands(commands: Array<Command>) {
            applyCommands.invoke(commands)
        }
    }

    setNavigator(navigator)

    return navigator
}

fun Router.customCommands(commands: Collection<Command>) {
    customCommands(*commands.toTypedArray())
}

fun Router.onCommandsApplied(onCommandsApplied: (Array<out Command>) -> Unit): NavigationListener {
    return addNavigationListener(onCommandsApplied = onCommandsApplied)
}

fun Router.onCommandApplied(onCommandApplied: (Command) -> Unit): NavigationListener {
    return addNavigationListener(onCommandApplied = onCommandApplied)
}

fun Router.addNavigationListener(
    onCommandsApplied: ((Array<out Command>) -> Unit)? = null,
    onCommandApplied: ((Command) -> Unit)? = null
): NavigationListener {
    val listener = object : NavigationListener {
        override fun onCommandsApplied(commands: Array<out Command>) {
            super.onCommandsApplied(commands)
            onCommandsApplied?.invoke(commands)
        }

        override fun onCommandApplied(command: Command) {
            onCommandApplied?.invoke(command)
        }
    }

    addNavigationListener(listener)

    return listener
}

fun Router.addResultListener(resultCode: Int, onResult: (Any) -> Unit): ResultListener {
    val listener = object : ResultListener {
        override fun onResult(result: Any) {
            onResult.invoke(result)
        }
    }

    addResultListener(resultCode, listener)

    return listener
}