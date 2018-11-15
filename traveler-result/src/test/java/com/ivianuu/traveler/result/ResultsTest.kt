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

package com.ivianuu.traveler.result

import com.ivianuu.traveler.Router
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ResultsTest {

    private val router = Router()
    private val listener = TestResultListener()

    @Test
    fun testResultListener() {
        router.addResultListener(1, listener)
        assertEquals(0, listener.calls)

        router.sendResult(1, 1)
        assertEquals(1, listener.calls)
        assertEquals(1, listener.history.last())

        router.sendResult(2, 2)
        assertEquals(1, listener.calls)
        assertEquals(1, listener.history.last())
    }

    @Test
    fun testRemoveResultListener() {
        router.addResultListener(1, listener)
        router.removeResultListener(1, listener)
        router.sendResult(1, 1)
        assertEquals(0, listener.calls)
    }

    @Test
    fun testResultListenerAddedTwice() {
        router.addResultListener(1, listener)
        router.addResultListener(1, listener)
        router.sendResult(1, 1)
        assertEquals(1, listener.calls)
    }
}

class TestResultListener : (Any) -> Unit {
    val history get() = _history
    private val _history = mutableListOf<Any>()
    val calls get() = _history.size
    override fun invoke(p1: Any) {
        _history.add(p1)
    }
}