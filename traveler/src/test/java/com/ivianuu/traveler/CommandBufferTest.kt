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

import com.ivianuu.traveler.util.TestCommand
import com.ivianuu.traveler.util.TestNavigator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommandBufferTest {

    private val commandBuffer = CommandBuffer()

    @Test
    fun testSetAndRemoveNavigator() {
        assertFalse(commandBuffer.hasNavigator)

        val navigator = TestNavigator()

        commandBuffer.setNavigator(navigator)
        assertTrue(commandBuffer.hasNavigator)

        commandBuffer.removeNavigator()
        assertFalse(commandBuffer.hasNavigator)
    }

    @Test
    fun testCommandEmission() {
        val navigator = TestNavigator()
        commandBuffer.setNavigator(navigator)
        val expectedState = mutableListOf<Command>()
        assertEquals(expectedState, navigator.history)

        expectedState.add(TestCommand(1))
        commandBuffer.executeCommands(arrayOf(TestCommand(1)))
        assertEquals(expectedState, navigator.history)

        expectedState.add(TestCommand(2))
        commandBuffer.executeCommands(arrayOf(TestCommand(2)))
        assertEquals(expectedState, navigator.history)

        expectedState.add(TestCommand(3))
        commandBuffer.executeCommands(arrayOf(TestCommand(3)))
        assertEquals(expectedState, navigator.history)
    }

    @Test
    fun testCommandBuffering() {
        val navigator = TestNavigator()

        val expectedState = mutableListOf<Command>(TestCommand(1))

        commandBuffer.executeCommands(arrayOf(TestCommand(1)))
        commandBuffer.setNavigator(navigator)
        assertEquals(expectedState, navigator.history)

        commandBuffer.removeNavigator()
        expectedState.add(TestCommand(2))
        commandBuffer.executeCommands(arrayOf(TestCommand(2)))
        commandBuffer.setNavigator(navigator)
        assertEquals(expectedState, navigator.history)
    }
}