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

package com.ivianuu.traveler.sample

import android.util.Log
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.RouterListener

private val routers = mutableMapOf<String, Router>()

fun router(key: String) = routers.getOrPut(key) {
    Router().apply { addListener(LoggingRouterListener(key)) }
}

private class LoggingRouterListener(private val key: String) : RouterListener {

    override fun onNavigatorSet(router: Router, navigator: Navigator) {
        log { "navigator set $navigator" }
    }

    override fun onNavigatorRemoved(router: Router, navigator: Navigator) {
        log { "navigator removed $navigator" }
    }

    override fun onCommandEnqueued(router: Router, command: Command) {
        log { "command enqueued $command" }
    }

    override fun preCommandApplied(router: Router, navigator: Navigator, command: Command) {
        log { "pre command applied $navigator $command" }
    }

    override fun postCommandApplied(router: Router, navigator: Navigator, command: Command) {
        log { "post command applied $navigator $command" }
    }

    private inline fun log(msg: () -> String) {
        Log.d("Router-$key", msg())
    }
}