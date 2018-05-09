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

package com.ivianuu.traveler.conductor

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.ivianuu.traveler.commands.*

/**
 * @author Manuel Wrage (IVIanuu)
 */
class ControllerNavigatorHelper(private val callback: Callback, private val router: Router) {

    fun forward(command: Forward): Boolean {
        val controller = callback.createController(command.key, command.data) ?: return false

        val tag = callback.getControllerTag(command.key)

        val transaction = RouterTransaction.with(controller)

        transaction.tag(tag)

        callback.setupRouterTransaction(command, transaction)

        router.pushController(transaction)

        return true
    }

    fun replace(command: Replace): Boolean {
        val controller = callback.createController(command.key, command.data) ?: return false

        val tag = callback.getControllerTag(command.key)

        val transaction = RouterTransaction.with(controller)
        transaction.tag(tag)

        callback.setupRouterTransaction(command, transaction)

        router.replaceTopController(transaction)

        return true
    }

    fun back(command: Back): Boolean {
        return router.popCurrentController()
    }

    fun backTo(command: BackTo): Boolean {
        val key = command.key

        return if (key == null) {
            router.popToRoot()
            true
        } else {
            val tag = callback.getControllerTag(key)

            if (router.getControllerWithTag(tag) == null) {
                false
            } else {
                router.popToTag(tag)
                true
            }
        }
    }

    interface Callback {

        fun createController(key: Any, data: Any?): Controller?

        fun getControllerTag(key: Any): String {
            return key.toString()
        }

        fun setupRouterTransaction(
            command: Command,
            transaction: RouterTransaction
        ) {
        }

    }
}