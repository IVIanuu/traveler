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

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import com.ivianuu.traveler.commands.Command

fun NavigatorHolder.setNavigator(applyCommand: (command: Command) -> Unit): Navigator {
    val navigator = object : Navigator {
        override fun applyCommand(command: Command) {
            applyCommand.invoke(command)
        }
    }

    setNavigator(navigator)

    return navigator
}

fun NavigatorHolder.setNavigator(
    lifecycleOwner: LifecycleOwner,
    navigator: Navigator
): LifecycleObserver = NavigatorLifecycleObserver.start(lifecycleOwner, this, navigator)

fun NavigatorHolder.setNavigator(
    lifecycleOwner: LifecycleOwner,
    applyCommand: (Command) -> Unit
): LifecycleObserver =
    NavigatorLifecycleObserver.start(lifecycleOwner, this, object : Navigator {
        override fun applyCommand(command: Command) {
            applyCommand.invoke(command)
        }
    })

fun Router.addResultListener(resultCode: Int, onResult: (Any) -> Unit): ResultListener {
    val listener = object : ResultListener {
        override fun onResult(result: Any) {
            onResult.invoke(result)
        }
    }

    addResultListener(resultCode, listener)

    return listener
}