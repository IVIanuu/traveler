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
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.RouterListener

private val routers = mutableMapOf<String, Router>()

fun router(key: String) = routers.getOrPut(key) {
    Router().apply { addRouterListener(LoggingRouterListener(key)) }
}

private class LoggingRouterListener(private val key: String) : RouterListener {

    override fun onCommandEnqueued(command: Command) {
        Log.d("Router-$key", "command enqueued $command")
    }

    override fun preCommandApplied(command: Command) {
        Log.d("Router-$key", "pre command applied $command")
    }

    override fun postCommandApplied(command: Command) {
        Log.d("Router-$key", "post command applied $command")
    }

}