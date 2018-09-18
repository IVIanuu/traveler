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

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.traveler.internal.NavigatorLifecycleObserver

/**
 * Holds a navigator.
 */
interface NavigatorHolder {

    /**
     * Whether or not a [Navigator] is currently set
     */
    val hasNavigator: Boolean

    /**
     * Sets the navigator which will be used to navigate
     */
    fun setNavigator(navigator: Navigator)

    /**
     * Removes the current [Navigator]
     */
    fun removeNavigator()
}

/**
 * Automatically attaches and removes the navigator in ON_RESUME and in ON_PAUSE
 * You should call this function only once for example in ON_CREATE
 */
fun NavigatorHolder.setNavigator(
    lifecycleOwner: LifecycleOwner,
    navigator: Navigator
): LifecycleObserver = NavigatorLifecycleObserver.start(lifecycleOwner, this, navigator)