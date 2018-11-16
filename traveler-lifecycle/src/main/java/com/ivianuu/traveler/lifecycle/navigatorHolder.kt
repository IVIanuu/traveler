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

package com.ivianuu.traveler.lifecycle

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.NavigatorHolder

/**
 * Sets the [navigator] and removes him on [event]
 */
fun NavigatorHolder.setNavigator(
    owner: LifecycleOwner,
    navigator: Navigator,
    event: Lifecycle.Event = Lifecycle.Event.ON_PAUSE
) {
    setNavigator(navigator)
    owner.lifecycle.addObserver(object : GenericLifecycleObserver {
        override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
            if (e == event) {
                owner.lifecycle.removeObserver(this)
                removeNavigator()
            }
        }
    })
}