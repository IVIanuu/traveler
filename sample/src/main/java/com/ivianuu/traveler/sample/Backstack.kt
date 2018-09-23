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

package com.ivianuu.traveler.sample

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.traveler.Back
import com.ivianuu.traveler.BackTo
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.NavigationListener
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.Router

/**
 * @author Manuel Wrage (IVIanuu)
 */
class Backstack(router: Router) {

    val backstack get() = _backstack.toList()
    private val _backstack = mutableListOf<Any>()

    private val listener: NavigationListener = { commands ->
        commands.forEach {
            when (it) {
                is Back -> onBack()
                is BackTo -> onBackTo(it)
                is Forward -> onForward(it)
                is Replace -> onReplace(it)
            }
        }
    }

    private val changeListeners = mutableSetOf<BackstackChangeListener>()

    init {
        router.addNavigationListener(listener)
    }

    fun addListener(listener: BackstackChangeListener) {
        changeListeners.add(listener)
    }

    fun addListener(
        owner: LifecycleOwner,
        event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
        listener: BackstackChangeListener
    ) {
        addListener(listener)
        owner.lifecycle.addObserver(object : GenericLifecycleObserver {
            override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {
                if (e == event) {
                    owner.lifecycle.removeObserver(this)
                    removeListener(listener)
                }
            }
        })
    }

    fun removeListener(listener: BackstackChangeListener) {
        changeListeners.remove(listener)
    }

    private fun onBack() {
        if (_backstack.isNotEmpty()) {
            _backstack.removeAt(_backstack.lastIndex)
        }

        dispatchChange()
    }

    private fun onBackTo(command: BackTo) {
        if (_backstack.isEmpty()) return

        val newBackstack = mutableListOf<Any>()
        if (command.key != null) {
            val index = _backstack.indexOf(command.key!!)
            if (index != -1) {
                newBackstack.addAll((0..index).map { _backstack[it] })
            } else {
                newBackstack.add(_backstack.first())
            }
        } else {
            newBackstack.add(_backstack.first())
        }

        _backstack.clear()
        _backstack.addAll(newBackstack)
        dispatchChange()
    }

    private fun onForward(command: Forward) {
        _backstack.add(command.key)
        dispatchChange()
    }

    private fun onReplace(command: Replace) {
        if (_backstack.isNotEmpty()) {
            _backstack.removeAt(_backstack.lastIndex)
        }

        _backstack.add(command.key)

        dispatchChange()
    }

    private fun dispatchChange() {
        val backstack = backstack
        changeListeners.forEach { it(backstack) }
    }
}

typealias BackstackChangeListener = (List<Any>) -> Unit