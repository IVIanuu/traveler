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
import com.ivianuu.traveler.Replace

/**
 * Helper for implementing a navigator for activities
 */
class AppNavigatorHelper(private val callback: Callback, private val context: Context) {

    fun forward(command: Forward): Boolean {
        val activityIntent =
            callback.createActivityIntent(context, command.key, command.data)

        return if (activityIntent != null) {
            val options = callback.createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.key, activityIntent, options)
            true
        } else {
            false
        }
    }

    fun replace(command: Replace): Boolean {
        val activityIntent =
            callback.createActivityIntent(context, command.key, command.data)

        return if (activityIntent != null) {
            val options = callback.createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.key, activityIntent, options)
            (context as? Activity
                ?: throw IllegalStateException("context must be an activity")).finish()
            true
        } else {
            false
        }
    }

    private fun checkAndStartActivity(key: Any, activityIntent: Intent, options: Bundle?) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(activityIntent, options)
        } else {
            callback.unexistingActivity(key, activityIntent)
        }
    }

    interface Callback {

        fun createActivityIntent(context: Context, key: Any, data: Any?): Intent? {
            return when (key) {
                is ActivityKey -> key.createIntent(context, data)
                else -> null
            }
        }

        fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? {

            return null
        }

        fun unexistingActivity(key: Any, activityIntent: Intent) {
            // Do nothing by default
        }
    }
}