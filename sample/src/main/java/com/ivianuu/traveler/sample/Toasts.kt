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

import android.content.Context
import android.widget.Toast
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.plugin.NavigatorPlugin

data class ToastCommand(val msg: CharSequence) : Command

class ToastPlugin(private val context: Context) : NavigatorPlugin {
    override fun handles(command: Command) = command is ToastCommand

    override fun apply(command: Command) {
        Toast.makeText(context, (command as ToastCommand).msg, Toast.LENGTH_SHORT).show()
    }
}

fun Router.showToast(msg: CharSequence) {
    executeCommands(ToastCommand(msg))
}