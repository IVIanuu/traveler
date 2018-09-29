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

package com.ivianuu.traveler.common

import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Navigator
import java.util.*

/**
 * A asynchronous [Navigator] which waits while a command is being processed
 */
abstract class AsyncNavigator : Navigator {

    /**
     * Will be checked before [applyCommand]
     */
    protected var canApplyCommands = true
        set(value) {
            field = value
            applyCommandIfPossible()
        }

    private val pendingCommands = LinkedList<PendingCommand>()

    final override fun applyCommand(command: Command) {
        enqueueCommand(command)
    }

    /**
     * Should apply the [command] and invoke [listener] when done
     */
    protected abstract fun applyCommand(command: Command, listener: () -> Unit)

    private fun enqueueCommand(command: Command) {
        val pendingCommand = PendingCommand(command)
        pendingCommands.add(pendingCommand)
        applyCommandIfPossible()
    }

    private fun applyCommandIfPossible() {
        if (pendingCommands.isNotEmpty() && canApplyCommands) {
            val command = pendingCommands.first
            if (command.status == PendingCommand.Status.ENQUEUED) {
                command.status = PendingCommand.Status.IN_PROGRESS
                applyCommandInternal(command)
            }
        }
    }

    private fun applyCommandInternal(command: PendingCommand) {
        applyCommand(command.command) {
            command.status = PendingCommand.Status.COMPLETED
            pendingCommands.removeFirst()
            applyCommandIfPossible()
        }
    }

    private data class PendingCommand(
        val command: Command,
        var status: Status = Status.ENQUEUED
    ) {
        enum class Status {
            ENQUEUED, IN_PROGRESS, COMPLETED
        }
    }
}

/**
 * Returns a new [AsyncNavigator] which uses [applyCommand]
 */
fun AsyncNavigator(block: (command: Command, listener: () -> Unit) -> Unit) =
    object : AsyncNavigator() {
        override fun applyCommand(command: Command, listener: () -> Unit) {
            block.invoke(command, listener)
        }
    }