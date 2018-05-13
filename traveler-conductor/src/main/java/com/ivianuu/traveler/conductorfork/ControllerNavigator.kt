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

package com.ivianuu.traveler.conductorfork

import com.bluelinelabs.conductor.Router
import com.ivianuu.traveler.BaseNavigator
import com.ivianuu.traveler.commands.*

/**
 * Navigator for controllers only
 */
abstract class ControllerNavigator(private val router: Router): BaseNavigator(), ControllerNavigatorHelper.Callback {

    private val controllerNavigatorHelper = ControllerNavigatorHelper(this, router)

    override fun forward(command: Forward) {
        if (!controllerNavigatorHelper.forward(command)) {
            unknownScreen(command)
        }
    }

    override fun replace(command: Replace) {
        if (!controllerNavigatorHelper.replace(command)) {
            unknownScreen(command)
        }
    }

    override fun back(command: Back) {
        if (!controllerNavigatorHelper.back(command)) {
            exit()
        }
    }

    override fun backTo(command: BackTo) {
        if (!controllerNavigatorHelper.backTo(command)) {
            backToUnexisting(command.key!!)
        }
    }

    protected open fun backToUnexisting(key: Any) {
        router.popToRoot()
    }

    protected open fun unknownScreen(command: Command) {
        throw IllegalArgumentException("unknown screen $command")
    }


    protected abstract fun exit()

}