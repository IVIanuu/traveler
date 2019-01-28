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
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.RouterListener
import com.ivianuu.traveler.addRouterListener
import com.ivianuu.traveler.setNavigator

/**
 * Sets the [navigator] and removes it on [event]
 */
fun Router.setNavigator(
    owner: LifecycleOwner,
    navigator: Navigator,
    event: Lifecycle.Event = Lifecycle.Event.ON_PAUSE
) {
    setNavigator(navigator)
    removeNavigatorOnEvent(owner, event)
}

/**
 * Sets the [navigator] which will be used to navigate
 */
fun Router.setNavigator(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_PAUSE,
    applyCommand: (command: Command) -> Unit
): Navigator {
    val navigator = setNavigator(applyCommand)
    removeNavigatorOnEvent(owner, event)
    return navigator
}

/**
 * Adds the [listener] and removes it on [event]
 */
fun Router.addRouterListener(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    listener: RouterListener
) {
    addRouterListener(listener)
    owner.lifecycle.addObserver(object : GenericLifecycleObserver {
        override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
            if (e == event) {
                owner.lifecycle.removeObserver(this)
                removeRouterListener(listener)
            }
        }
    })
}

/**
 * Adds the [listener] and removes it on [event]
 */
fun Router.addRouterListener(
    owner: LifecycleOwner,
    event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    onCommandEnqueued: ((command: Command) -> Unit)? = null,
    preCommandApplied: ((navigator: Navigator, command: Command) -> Unit)? = null,
    postCommandApplied: ((navigator: Navigator, command: Command) -> Unit)? = null
): RouterListener {
    val listener = addRouterListener(onCommandEnqueued, preCommandApplied, postCommandApplied)
    owner.lifecycle.addObserver(object : GenericLifecycleObserver {
        override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
            if (e == event) {
                owner.lifecycle.removeObserver(this)
                removeRouterListener(listener)
            }
        }
    })

    return listener
}

private fun Router.removeNavigatorOnEvent(
    owner: LifecycleOwner,
    event: Lifecycle.Event
) {
    owner.lifecycle.addObserver(object : GenericLifecycleObserver {
        override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
            if (e == event) {
                owner.lifecycle.removeObserver(this)
                removeNavigator()
            }
        }
    })
}