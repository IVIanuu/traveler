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

import com.ivianuu.traveler.commands.Command

/**
 * Base router
 */
abstract class BaseRouter {
    internal val commandBuffer = CommandBuffer()

    fun executeCommand(command: Command) {
        executeCommands(command)
    }

    fun executeCommands(vararg commands: Command) {
        commandBuffer.executeCommands(arrayOf(*commands))
    }
}
