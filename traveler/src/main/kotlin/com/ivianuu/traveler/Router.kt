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
 * The object used to push around
 */
interface Router {

    /**
     * The current navigator attached to this router
     */
    val navigator: Navigator?

    /**
     * Sets the [navigator] which will be used to push
     */
    fun setNavigator(navigator: Navigator)

    /**
     * Removes the current [Navigator]
     */
    fun removeNavigator()

    /**
     * Sends the [commands] to the [Navigator]
     */
    fun enqueueCommands(vararg commands: Command)

    /**
     * Adds the [listener]
     */
    fun addListener(listener: RouterListener)

    /**
     * Removes the previously added [listener]
     */
    fun removeListener(listener: RouterListener)

}

/**
 * Returns a new [Router] instance
 */
fun Router(): Router = RealRouter()

/**
 * Whether or not a [Navigator] is currently set
 */
val Router.hasNavigator: Boolean get() = navigator != null

/**
 * Sets the [navigator] which will be used to push
 */
fun Router.setNavigator(navigator: (command: Command) -> Unit): Navigator {
    return object : Navigator {
        override fun applyCommand(command: Command) {
            navigator.invoke(command)
        }
    }.also { setNavigator(it) }
}

/**
 * Sends the [commands] to the [Navigator]
 */
fun Router.enqueueCommands(commands: Collection<Command>) {
    enqueueCommands(*commands.toTypedArray())
}

/**
 * Navigates forward to [key]
 */
fun Router.push(key: Any, data: Any? = null) {
    enqueueCommands(Forward(key, data))
}

/**
 * Clears all screen and opens [key]
 */
fun Router.setRoot(key: Any, data: Any? = null) {
    enqueueCommands(
        BackTo(null),
        Replace(key, data)
    )
}

/**
 * Replaces the top screen with [key]
 */
fun Router.replaceTop(key: Any, data: Any? = null) {
    enqueueCommands(Replace(key, data))
}

/**
 * Goes back to [key]
 */
fun Router.popTo(key: Any) {
    enqueueCommands(BackTo(key))
}

/**
 * Goes back to the root screen
 */
fun Router.popToRoot() {
    enqueueCommands(BackTo(null))
}

/**
 * Goes back to the previous screen
 */
fun Router.pop() {
    enqueueCommands(Back)
}

/**
 * Finishes the chain
 */
fun Router.finish() {
    enqueueCommands(
        BackTo(null),
        Back
    )
}

/**
 * Adds the [listener]
 */
fun Router.addListener(
    onNavigatorSet: ((router: Router, navigator: Navigator) -> Unit)? = null,
    onNavigatorRemoved: ((router: Router, navigator: Navigator) -> Unit)? = null,
    onCommandEnqueued: ((router: Router, command: Command) -> Unit)? = null,
    preCommandApplied: ((router: Router, navigator: Navigator, command: Command) -> Unit)? = null,
    postCommandApplied: ((router: Router, navigator: Navigator, command: Command) -> Unit)? = null
): RouterListener {
    return object : RouterListener {
        override fun onNavigatorSet(router: Router, navigator: Navigator) {
            onNavigatorSet?.invoke(router, navigator)
        }

        override fun onNavigatorRemoved(router: Router, navigator: Navigator) {
            onNavigatorRemoved?.invoke(router, navigator)
        }

        override fun onCommandEnqueued(router: Router, command: Command) {
            onCommandEnqueued?.invoke(router, command)
        }

        override fun preCommandApplied(router: Router, navigator: Navigator, command: Command) {
            preCommandApplied?.invoke(router, navigator, command)
        }

        override fun postCommandApplied(router: Router, navigator: Navigator, command: Command) {
            postCommandApplied?.invoke(router, navigator, command)
        }
    }.also { addListener(it) }
}