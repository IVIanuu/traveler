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
 * Base navigator which processes commands and maps them to functions
 */
abstract class BaseNavigator : Navigator {

    override fun invoke(commands: Array<out Command>) {
        commands.forEach { applyCommand(it) }
    }

    /**
     * Maps the command to one of the built in functions
     */
    protected open fun applyCommand(command: Command) {
        when(command) {
            is Back -> back(command)
            is BackTo -> backTo(command)
            is Forward -> forward(command)
            is Replace -> replace(command)
            else -> throwUnsupportedCommand(command)
        }
    }

    /**
     * Should navigate forward to the screen for [Forward.key]
     */
    protected open fun forward(command: Forward) {
        throwUnsupportedCommand(command)
    }

    /**
     * Should replace the current screen with the screen for [Replace.key]
     */
    protected open fun replace(command: Replace) {
        throwUnsupportedCommand(command)
    }

    /**
     * Should go back to the previous screen
     */
    protected open fun back(command: Back) {
        throwUnsupportedCommand(command)
    }

    /**
     * Should go back to [BackTo.key]
     */
    protected open fun backTo(command: BackTo) {
        throwUnsupportedCommand(command)
    }

    /**
     * Will be called when [backTo] was called with an unknown screen
     */
    protected open fun backToUnexisting(key: Any) {
    }

    /**
     * Will be called when a unknown screen was requested
     */
    protected open fun unknownScreen(command: Command) {
        throw IllegalArgumentException("unknown screen $command")
    }

    /**
     * Will be called when a unsupported command was send
     */
    protected open fun throwUnsupportedCommand(command: Command) {
        throw IllegalArgumentException("unsupported command $command")
    }
}