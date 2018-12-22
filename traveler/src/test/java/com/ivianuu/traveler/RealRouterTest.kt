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
import com.ivianuu.traveler.util.TestRouterListener
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RealRouterTest {

    private val router = RealRouter()
    private val listener = TestRouterListener()

    @Test
    fun testSetAndRemoveNavigator() {
        assertFalse(router.hasNavigator)

        val navigator = TestNavigator()

        router.setNavigator(navigator)
        assertTrue(router.hasNavigator)

        router.removeNavigator()
        assertFalse(router.hasNavigator)
    }

    @Test
    fun testCommandEmission() {
        val navigator = TestNavigator()
        router.setNavigator(navigator)
        val expectedState = mutableListOf<Command>()
        assertEquals(expectedState, navigator.history)

        expectedState.add(TestCommand(1))
        router.enqueueCommands(TestCommand(1))
        assertEquals(expectedState, navigator.history)

        expectedState.add(TestCommand(2))
        router.enqueueCommands(TestCommand(2))
        assertEquals(expectedState, navigator.history)

        expectedState.add(TestCommand(3))
        router.enqueueCommands(TestCommand(3))
        assertEquals(expectedState, navigator.history)
    }

    @Test
    fun testCommandBuffering() {
        val navigator = TestNavigator()

        val expectedState = mutableListOf<Command>(TestCommand(1))

        router.enqueueCommands(TestCommand(1))
        router.setNavigator(navigator)
        assertEquals(expectedState, navigator.history)

        router.removeNavigator()
        expectedState.add(TestCommand(2))
        router.enqueueCommands(TestCommand(2))
        router.setNavigator(navigator)
        assertEquals(expectedState, navigator.history)
    }

    @Test
    fun testRouterListener() {
        router.setNavigator(TestNavigator())

        router.addRouterListener(listener)
        assertEquals(0, listener.commandEnqueuedCalls)
        assertEquals(0, listener.preCommandAppliedCalls)
        assertEquals(0, listener.postCommandAppliedCalls)

        val expectedState = mutableListOf<Command>()

        expectedState.add(TestCommand(1))
        router.enqueueCommands(TestCommand(1))

        assertEquals(expectedState, listener.commandEnqueuedHistory)
        assertEquals(1, listener.commandEnqueuedCalls)
        assertEquals(expectedState, listener.preCommandAppliedHistory)
        assertEquals(1, listener.preCommandAppliedCalls)
        assertEquals(expectedState, listener.postCommandAppliedHistory)
        assertEquals(1, listener.postCommandAppliedCalls)

        expectedState.add(TestCommand(2))
        expectedState.add(TestCommand(3))

        router.enqueueCommands(TestCommand(2), TestCommand(3))

        assertEquals(expectedState, listener.commandEnqueuedHistory)
        assertEquals(3, listener.commandEnqueuedCalls)
        assertEquals(expectedState, listener.preCommandAppliedHistory)
        assertEquals(3, listener.preCommandAppliedCalls)
        assertEquals(expectedState, listener.postCommandAppliedHistory)
        assertEquals(3, listener.postCommandAppliedCalls)
    }

    @Test
    fun testRemoveRouterListener() {
        router.setNavigator(TestNavigator())

        router.addRouterListener(listener)
        router.removeRouterListener(listener)

        router.enqueueCommands(TestCommand())

        assertEquals(0, listener.commandEnqueuedCalls)
        assertEquals(0, listener.preCommandAppliedCalls)
        assertEquals(0, listener.postCommandAppliedCalls)
    }

    @Test
    fun testRouterListenerAddedTwice() {
        router.setNavigator(TestNavigator())

        router.addRouterListener(listener)
        router.addRouterListener(listener)
        router.enqueueCommands(TestCommand())
        assertEquals(1, listener.commandEnqueuedCalls)
        assertEquals(1, listener.preCommandAppliedCalls)
        assertEquals(1, listener.postCommandAppliedCalls)
    }
}