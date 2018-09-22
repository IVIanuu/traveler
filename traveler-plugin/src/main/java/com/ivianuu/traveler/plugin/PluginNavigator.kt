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

package com.ivianuu.traveler.plugin

import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Navigator

/**
 * A navigator which uses [NavigatorPlugin]'s to apply commands
 */
open class PluginNavigator(
    private val plugins: List<NavigatorPlugin>
) : Navigator {

    override fun invoke(commands: Array<out Command>) {
        commands.forEach { command ->
            val plugin = plugins.firstOrNull { it.handles(command) }
                ?: throw IllegalArgumentException("no plugin handles $command")
            plugin.apply(command)
        }
    }
}

/**
 * Returns a new [PluginNavigator] with [plugins]
 */
fun pluginNavigatorOf(vararg plugins: NavigatorPlugin) =
    PluginNavigator(plugins.toList())

/**
 * Returns a new [PluginNavigator] with [plugins]
 */
fun pluginNavigatorOf(plugins: Collection<NavigatorPlugin>) =
    PluginNavigator(plugins.toList())