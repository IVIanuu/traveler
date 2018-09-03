package com.ivianuu.traveler.internal

import android.os.Handler
import android.os.Looper

internal val handler = Handler(Looper.getMainLooper())

internal val isMainThread get() = Looper.myLooper() == Looper.getMainLooper()

internal fun mainThread(action: () -> Unit) {
    when {
        isMainThread -> action()
        else -> handler.post(action)
    }
}