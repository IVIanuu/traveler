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

import com.ivianuu.director.Router
import com.ivianuu.traveler.Back
import com.ivianuu.traveler.BackTo
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.SimpleNavigator

/**
 * Navigator for fragments only
 */
open class ControllerNavigator(
    router: Router
) : SimpleNavigator(), ControllerNavigatorHelper.Callback {

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

    override fun backToUnexisting(key: Any) {
        controllerNavigatorHelper.backToRoot()
    }

    protected open fun exit() {
    }
}