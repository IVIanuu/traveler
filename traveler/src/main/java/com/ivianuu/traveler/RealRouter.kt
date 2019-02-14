package com.ivianuu.traveler

import android.os.Handler
import android.os.Looper
import java.util.*

/**
 * The actual implementation of a [Router]
 */
open class RealRouter : Router {

    override val hasNavigator: Boolean
        get() = navigator != null

    private var navigator: Navigator? = null
    private val pendingCommands = LinkedList<Command>()

    private val handler = Handler(Looper.getMainLooper())
    private val isMainThread get() = Looper.myLooper() == Looper.getMainLooper()

    private val routerListeners = mutableSetOf<RouterListener>()

    override fun setNavigator(navigator: Navigator) {
        requireMainThread()
        this.navigator = navigator
        while (!pendingCommands.isEmpty()) {
            executeCommand(pendingCommands.poll())
        }
    }

    override fun removeNavigator() {
        requireMainThread()
        this.navigator = null
    }

    override fun enqueueCommands(vararg commands: Command) = mainThread {
        commands.forEach { command ->
            routerListeners.toList().forEach { it.onCommandEnqueued(command) }
            executeCommand(command)
        }
    }

    override fun addRouterListener(listener: RouterListener) {
        requireMainThread()
        routerListeners.add(listener)
    }

    override fun removeRouterListener(listener: RouterListener) {
        requireMainThread()
        routerListeners.remove(listener)
    }

    private fun executeCommand(command: Command) {
        val navigator = navigator
        if (navigator != null) {
            routerListeners.toList().forEach { it.preCommandApplied(navigator, command) }
            navigator.applyCommand(command)
            routerListeners.toList().forEach { it.postCommandApplied(navigator, command) }
        } else {
            pendingCommands.add(command)
        }
    }

    private fun mainThread(action: () -> Unit) {
        if (isMainThread) {
            action()
        } else {
            handler.post(action)
        }
    }

    private fun requireMainThread() {
        if (!isMainThread) throw IllegalArgumentException("must be called from the main thread")
    }
}