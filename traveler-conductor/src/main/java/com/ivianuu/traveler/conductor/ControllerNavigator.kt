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
import com.ivianuu.traveler.BaseNavigator
import com.ivianuu.traveler.commands.*

/**
 * Navigator for controllers only
 */
abstract class ControllerNavigator(private val router: Router): BaseNavigator() {

    override fun forward(command: Forward) {
        val controller = createController(command.key, command.data)

        if (controller == null) {
            unknownScreen(command)
            return
        }


        val tag = getControllerTag(command.key)

        if (tag == null) {
            unknownScreen(command)
            return
        }

        val transaction = RouterTransaction.with(controller)
        transaction.tag(tag)

        setupRouterTransaction(command, transaction)

        router.pushController(transaction)
    }

    override fun replace(command: Replace) {
        val controller = createController(command.key, command.data)

        if (controller == null) {
            unknownScreen(command)
            return
        }

        val tag = getControllerTag(command.key)

        if (tag == null) {
            unknownScreen(command)
            return
        }

        val transaction = RouterTransaction.with(controller)
        transaction.tag(tag)

        setupRouterTransaction(command, transaction)

        router.replaceTopController(transaction)
    }

    override fun back(command: Back) {
        if (!router.popCurrentController()) {
            exit()
        }
    }

    override fun backTo(command: BackTo) {
        val key = command.key

        if (key == null) {
            router.popToRoot()
        } else {
            val tag = getControllerTag(key)

            if (tag == null) {
                unknownScreen(command)
                return
            }

            if (router.getControllerWithTag(tag) == null) {
                backToUnexisting(command.key!!)
            } else {
                router.popToTag(tag)
            }
        }
    }

    open fun setupRouterTransaction(
        command: Command,
        transaction: RouterTransaction
    ) {
    }

    protected open fun backToUnexisting(key: Any) {
        router.popToRoot()
    }

    protected open fun unknownScreen(command: Command) {
        throw RuntimeException("unknown screen $command")
    }

    protected open fun getControllerTag(key: Any): String? {
        return key.toString()
    }

    protected abstract fun createController(key: Any, data: Any?): Controller?

    protected abstract fun exit()

}