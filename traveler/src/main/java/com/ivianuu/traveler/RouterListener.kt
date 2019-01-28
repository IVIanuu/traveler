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
 * Listener for navigation
 */
interface RouterListener {

    /**
     * Will be called when the [command] gets enqueued
     */
    fun onCommandEnqueued(command: Command) {
    }

    /**
     * Will be called right before the [command] will be applied by the [navigator]
     */
    fun preCommandApplied(navigator: Navigator, command: Command) {
    }

    /**
     * Will be called right after the [command] was applied by the [navigator]
     */
    fun postCommandApplied(navigator: Navigator, command: Command) {
    }

}