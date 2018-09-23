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
import kotlin.reflect.KClass

/**
 * A key which knows how create intents
 */
interface ActivityKey {
    fun createIntent(context: Context, data: Any?): Intent
    fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? = null
}

/**
 * Returns a new [ActivityKey]
 */
fun <T : Activity> ActivityKey(clazz: KClass<T>): ActivityKey = object : ActivityKey {
    override fun createIntent(context: Context, data: Any?) = Intent(context, clazz.java)
}

/**
 * Returns a new [ActivityKey]
 */
inline fun <reified T : Activity> ActivityKey() = ActivityKey(T::class)

/**
 * Returns a new [ActivityKey]
 */
fun ActivityKey(intent: Intent): ActivityKey = object : ActivityKey {
    override fun createIntent(context: Context, data: Any?) = intent
}