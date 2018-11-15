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
import com.ivianuu.traveler.util.TestNavigationListener
import com.ivianuu.traveler.util.TestNavigator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RouterTest {

    private val router = Router().apply { commandBuffer.setNavigator(TestNavigator()) }
    private val listener = TestNavigationListener()

    @Test
    fun testNavigationListener() {
        router.addNavigationListener(listener)
        assertEquals(0, listener.calls)

        val expectedState = mutableListOf<Command>()

        expectedState.add(TestCommand(1))
        router.executeCommands(TestCommand(1))

        assertEquals(expectedState, listener.history)
        assertEquals(1, listener.calls)

        expectedState.add(TestCommand(2))
        expectedState.add(TestCommand(3))
        router.executeCommands(TestCommand(2), TestCommand(3))

        assertEquals(expectedState, listener.history)
        assertEquals(3, listener.calls)
    }

    @Test
    fun testRemoveNavigationListener() {
        router.addNavigationListener(listener)
        router.removeNavigationListener(listener)
        router.executeCommands(TestCommand())
        assertEquals(0, listener.calls)
    }

    @Test
    fun testNavigationListenerAddedTwice() {
        router.addNavigationListener(listener)
        router.addNavigationListener(listener)
        router.executeCommands(TestCommand())
        assertEquals(1, listener.calls)
    }
}