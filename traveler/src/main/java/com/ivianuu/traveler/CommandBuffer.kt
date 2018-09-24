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

import android.os.Handler
import android.os.Looper
import java.util.*

/**
 * Passes navigation command to an active [Navigator]
 * or stores it in the pending commands queue to pass it later.
 */
internal class CommandBuffer : NavigatorHolder {

    override val hasNavigator: Boolean
        get() = navigator != null

    private var navigator: Navigator? = null
    private val pendingCommands = LinkedList<Array<out Command>>()

    private val handler = Handler(Looper.getMainLooper())
    private val isMainThread get() = Looper.myLooper() == Looper.getMainLooper()

    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        while (!pendingCommands.isEmpty()) {
            executeCommands(pendingCommands.poll())
        }
    }

    override fun removeNavigator() {
        this.navigator = null
    }

    fun executeCommands(commands: Array<out Command>) = mainThread {
        val navigator = navigator
        if (navigator != null) {
            navigator.applyCommands(commands)
        } else {
            pendingCommands.add(commands)
        }
    }

    private fun mainThread(action: () -> Unit) {
        when {
            isMainThread -> action()
            else -> handler.post(action)
        }
    }
}
