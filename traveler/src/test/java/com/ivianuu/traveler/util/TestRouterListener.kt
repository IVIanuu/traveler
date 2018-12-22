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

package com.ivianuu.traveler.util

import com.ivianuu.traveler.Command
import com.ivianuu.traveler.RouterListener

class TestRouterListener : RouterListener {

    val commandEnqueuedHistory get() = _commandEnqueuedHistory.toList()
    private val _commandEnqueuedHistory = mutableListOf<Command>()
    val commandEnqueuedCalls get() = _commandEnqueuedHistory.size

    val preCommandAppliedHistory get() = _preCommandAppliedHistory.toList()
    private val _preCommandAppliedHistory = mutableListOf<Command>()
    val preCommandAppliedCalls get() = _preCommandAppliedHistory.size

    val postCommandAppliedHistory get() = _postCommandAppliedHistory.toList()
    private val _postCommandAppliedHistory = mutableListOf<Command>()
    val postCommandAppliedCalls get() = _postCommandAppliedHistory.size

    override fun onCommandEnqueued(command: Command) {
        _commandEnqueuedHistory.add(command)
    }

    override fun preCommandApplied(command: Command) {
        _preCommandAppliedHistory.add(command)
    }

    override fun postCommandApplied(command: Command) {
        _postCommandAppliedHistory.add(command)
    }

}