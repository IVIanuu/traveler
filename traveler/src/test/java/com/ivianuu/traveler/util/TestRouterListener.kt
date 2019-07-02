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
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.RouterListener

class TestRouterListener : RouterListener {

    private val _commandEnqueuedHistory = mutableListOf<Command>()
    val commandEnqueuedHistory get() = _commandEnqueuedHistory.toList()
    val commandEnqueuedCalls get() = _commandEnqueuedHistory.size

    private val _preCommandAppliedHistory = mutableListOf<Command>()
    val preCommandAppliedHistory get() = _preCommandAppliedHistory.toList()
    val preCommandAppliedCalls get() = _preCommandAppliedHistory.size

    private val _postCommandAppliedHistory = mutableListOf<Command>()
    val postCommandAppliedHistory get() = _postCommandAppliedHistory.toList()
    val postCommandAppliedCalls get() = _postCommandAppliedHistory.size

    override fun onCommandEnqueued(router: Router, command: Command) {
        _commandEnqueuedHistory.add(command)
    }

    override fun preCommandApplied(router: Router, navigator: Navigator, command: Command) {
        _preCommandAppliedHistory.add(command)
    }

    override fun postCommandApplied(router: Router, navigator: Navigator, command: Command) {
        _postCommandAppliedHistory.add(command)
    }

}