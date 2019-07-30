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

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ivianuu.traveler.Command

/**
 * A key for activities/intents
 */
interface ActivityKey {

    /**
     * Returns the [Intent] for this key
     */
    fun createIntent(context: Context, data: Any?): Intent

    /**
     * Returns the [ActivityOptions] for this key
     */
    fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? = null

}