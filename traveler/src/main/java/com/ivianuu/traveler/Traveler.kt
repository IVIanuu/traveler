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
 * Traveler is the holder for other library components.
 */
class Traveler<T : Router> private constructor(val router: T) {

    /**
     * Returns the navigator holder
     */
    val navigatorHolder: NavigatorHolder
        get() = router.commandBuffer

    companion object {

        /**
         * Returns a new [Traveler] with the default [Router]
         */
        operator fun invoke() = invoke(Router())

        /**
         * Returns a new [Traveler] with a custom [Router]
         */
        operator fun <T : Router> invoke(router: T) = Traveler(router)

    }
}

fun <T : Router> Traveler<T>.component1() = router

fun <T : Router> Traveler<T>.component2() = navigatorHolder