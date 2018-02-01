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

package com.ivianuu.traveler.supportfragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.ivianuu.traveler.commands.Command
import com.ivianuu.traveler.commands.Forward
import com.ivianuu.traveler.commands.Replace

/**
 * A [SupportFragmentNavigator] which allows to navigate to [Activity]'s
 */
abstract class SupportAppNavigator @JvmOverloads constructor(
    private val activity: FragmentActivity,
    fragmentManager: FragmentManager = activity.supportFragmentManager,
    containerId: Int
) : SupportFragmentNavigator(fragmentManager, containerId) {

    override fun forward(command: Forward) {
        val activityIntent =
            createActivityIntent(activity, command.key)

        // Start activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.key, activityIntent, options)
        } else {
            super.forward(command)
        }
    }

    override fun replace(command: Replace) {
        val activityIntent =
            createActivityIntent(activity, command.key)

        // Replace activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(command.key, activityIntent, options)
            activity.finish()
        } else {
            super.replace(command)
        }
    }

    override fun showSystemMessage(message: String) {
        // Toast by default
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun exit() {
        // Finish by default
        activity.finish()
    }

    protected open fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? {
        return null
    }

    protected fun unexistingActivity(key: Any, intent: Intent) {
        // Do nothing by default
    }

    protected abstract fun createActivityIntent(
        context: Context?,
        key: Any
    ): Intent?

    private fun checkAndStartActivity(key: Any, activityIntent: Intent, options: Bundle?) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(activityIntent, options)
        } else {
            unexistingActivity(key, activityIntent)
        }
    }
}