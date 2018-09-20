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

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.NavigatorHolder

/**
 * A [LifecycleObserver] which automatically sets and removes the navigator on the corresponding lifecycle events
 */
internal class NavigatorLifecycleObserver private constructor(
    lifecycleOwner: LifecycleOwner,
    private val navigatorHolder: NavigatorHolder,
    private val navigator: Navigator
) : GenericLifecycleObserver {

    init {
        // if its a activity we must add a dummy fragment to make sure
        // that we do NOT set the navigator before onResumeFragments was called
        // otherwise this would lead to crashes
        val lifecycle = if (lifecycleOwner is FragmentActivity) {
            LifecycleProviderFragment.get(
                lifecycleOwner
            ).lifecycle
        } else {
            lifecycleOwner.lifecycle
        }

        // observe
        lifecycle.addObserver(this)

        // update navigator state based on the current lifecycle state
        updateState(lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED))
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        updateState(source.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED))
    }

    private fun updateState(shouldAttach: Boolean) {
        if (shouldAttach) {
            if (!navigatorHolder.hasNavigator) {
                navigatorHolder.setNavigator(navigator)
            }
        } else {
            if (navigatorHolder.hasNavigator) {
                navigatorHolder.removeNavigator()
            }
        }
    }

    companion object {

        fun start(
            lifecycleOwner: LifecycleOwner,
            navigatorHolder: NavigatorHolder,
            navigator: Navigator
        ): LifecycleObserver =
            NavigatorLifecycleObserver(
                lifecycleOwner,
                navigatorHolder,
                navigator
            )

    }
}