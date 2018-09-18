package com.ivianuu.traveler.internal

import com.ivianuu.traveler.ResultListener

/**
 * @author Manuel Wrage (IVIanuu)
 */
internal object Results {

    private val resultListeners = mutableMapOf<Int, MutableSet<ResultListener>>()

    fun addResultListener(resultCode: Int, listener: ResultListener) {
        val listeners = resultListeners.getOrPut(resultCode) { mutableSetOf() }
        listeners.add(listener)
    }

    fun removeResultListener(resultCode: Int, listener: ResultListener) {
        val listeners = resultListeners[resultCode] ?: return
        listeners.remove(listener)
        if (listeners.isEmpty()) {
            resultListeners.remove(resultCode)
        }
    }

    fun sendResult(resultCode: Int, result: Any): Boolean {
        val listeners = resultListeners[resultCode]?.toList()
        if (listeners != null) {
            listeners.forEach { it(result) }
            return true
        }

        return false
    }
}