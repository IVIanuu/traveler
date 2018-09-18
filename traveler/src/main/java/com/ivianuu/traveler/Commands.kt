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

package com.ivianuu.traveler

/**
 * Navigation command describes screens transition.
 * that can be processed by [Navigator].
 */
interface Command

/**
 * Goes back to the previous screen.
 */
object Back : Command {
    override fun toString() = "Back"
}

/**
 * Goes back to the screen with [key]
 * Or to the root if [null]
 */
data class BackTo(val key: Any?) : Command

/**
 * Opens a new screen.
 */
data class Forward(val key: Any, val data: Any?) : Command

/**
 * Replaces the current screen.
 */
data class Replace(val key: Any, val data: Any?) : Command