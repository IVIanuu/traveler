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

package com.ivianuu.traveler.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.common.ResultNavigator

/**
 * A [Navigator] for [Activity]'s
 */
open class AppNavigator(private val context: Context) : ResultNavigator() {

    override fun applyCommandWithResult(command: Command): Boolean {
        return when (command) {
            is Forward -> forward(command)
            is Replace -> replace(command)
            else -> unsupportedCommand(command)
        }
    }

    protected open fun forward(command: Forward): Boolean {
        val activityIntent =
            createActivityIntent(context, command.key, command.data)

        return if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.key, activityIntent, options)
        } else {
            unknownScreen(command.key)
        }
    }

    protected open fun replace(command: Replace): Boolean {
        val activityIntent =
            createActivityIntent(context, command.key, command.data)

        return if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.key, activityIntent, options)
            (context as? Activity
                ?: throw IllegalStateException("context must be an activity")).finish()
            true
        } else {
            unknownScreen(command.key)
        }
    }

    protected open fun createActivityIntent(context: Context, key: Any, data: Any?): Intent? {
        return when (key) {
            is ActivityKey -> key.createIntent(context, data)
            else -> null
        }
    }

    protected open fun createStartActivityOptions(
        command: Command,
        activityIntent: Intent
    ): Bundle? {
        val key = when (command) {
            is Forward -> command.key
            is Replace -> command.key
            else -> null
        } as? ActivityKey ?: return null

        return key.createStartActivityOptions(command, activityIntent)
    }

    /**
     * Will be called when the [activityIntent] is not resolvable
     */
    protected open fun unexistingActivity(key: Any, activityIntent: Intent) = true

    /**
     * Will be called when a unknown screen was requested
     */
    protected open fun unknownScreen(key: Any) = false

    /**
     * Will be called when a unsupported command was send
     */
    protected open fun unsupportedCommand(command: Command) = false

    private fun checkAndStartActivity(
        key: Any,
        activityIntent: Intent,
        options: Bundle?
    ): Boolean {
        return if (activityIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(activityIntent, options)
            true
        } else {
            unexistingActivity(key, activityIntent)
        }
    }
}

/**
 * Returns a new [AppNavigator]
 */
fun Context.AppNavigator() = AppNavigator(this)