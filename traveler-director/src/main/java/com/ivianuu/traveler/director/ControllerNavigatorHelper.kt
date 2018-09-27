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

package com.ivianuu.traveler.director

import com.ivianuu.director.Controller
import com.ivianuu.director.Router
import com.ivianuu.director.RouterTransaction
import com.ivianuu.director.tag
import com.ivianuu.director.toTransaction
import com.ivianuu.traveler.Back
import com.ivianuu.traveler.BackTo
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Replace

/**
 * Helper for implementing a navigator for controllers
 */
class ControllerNavigatorHelper(
    private val callback: Callback,
    private val router: Router
) {

    fun forward(command: Forward): Boolean {
        val controller = callback.createController(command.key, command.data) ?: return false

        val tag = callback.getControllerTag(command.key)

        val transaction = controller.toTransaction().tag(tag)

        callback.setupTransaction(command, transaction)

        router.pushController(transaction)

        return true
    }

    fun replace(command: Replace): Boolean {
        val controller = callback.createController(command.key, command.data) ?: return false

        val tag = callback.getControllerTag(command.key)

        val transaction = controller.toTransaction().tag(tag)

        callback.setupTransaction(command, transaction)

        router.replaceTopController(transaction)

        return true
    }

    fun back(command: Back): Boolean {
        return router.handleBack()
    }

    fun backTo(command: BackTo): Boolean {
        val key = command.key

        return if (key == null) {
            backToRoot()
            true
        } else {
            router.popToTag(callback.getControllerTag(key))
        }
    }

    fun backToRoot() {
        router.popToRoot()
    }

    interface Callback {

        fun createController(key: Any, data: Any?): Controller? {
            return when (key) {
                is ControllerKey -> key.createController(data)
                else -> null
            }
        }

        fun getControllerTag(key: Any) = when (key) {
            is ControllerKey -> key.getControllerTag()
            else -> key.toString()
        }

        fun setupTransaction(
            command: Command,
            transaction: RouterTransaction
        ) {
            val key = when (command) {
                is Forward -> command.key
                is Replace -> command.key
                else -> null
            } as? ControllerKey ?: return

            key.setupTransaction(command, transaction)
        }
    }
}