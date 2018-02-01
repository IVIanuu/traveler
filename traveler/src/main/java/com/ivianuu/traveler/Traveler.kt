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
 * To use it, instantiate it using one of the [.create] methods.
 * When you need a [NavigatorHolder] or router, get it here.
 */
class Traveler<out T : BaseRouter> private constructor(val router: T) {

    val navigatorHolder: NavigatorHolder
        get() = router.commandBuffer

    companion object {

        @JvmStatic
        fun create(): Traveler<Router> {
            return create(Router())
        }

        @JvmStatic
        fun <T : BaseRouter> create(customRouter: T): Traveler<T> {
            return Traveler(customRouter)
        }
    }
}