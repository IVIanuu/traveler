package com.ivianuu.traveler.android

import android.os.Handler
import android.os.Looper
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.RealRouter
import com.ivianuu.traveler.hasNavigator

/**
 * Ensures that all [Command]s are passed to [Navigator]s on the main thread
 */
open class AndroidRouter : RealRouter() {

    private val handler = Handler(Looper.getMainLooper())
    private val isMainThread get() = Looper.myLooper() == Looper.getMainLooper()

    override fun enqueueCommands(vararg commands: Command) {
        if (isMainThread) {
            super.enqueueCommands(*commands)
        } else if (hasNavigator) {
            mainThread { super.enqueueCommands(*commands) }
        }
    }

    override fun executePendingCommands() = mainThread {
        super.executePendingCommands()
    }

    private fun mainThread(action: () -> Unit) {
        if (isMainThread) {
            action()
        } else {
            handler.post(action)
        }
    }
}