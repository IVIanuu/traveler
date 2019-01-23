package com.ivianuu.traveler

import android.os.Handler
import android.os.Looper
import java.util.*

/**
 * The actual implementation of a [router]
 */
open class RealRouter : Router {

    /**
     * Whether or not a [Navigator] is currently set
     */
    override val hasNavigator: Boolean
        get() = navigator != null

    private var navigator: Navigator? = null
    private val pendingCommands = LinkedList<Command>()

    private val handler = Handler(Looper.getMainLooper())
    private val isMainThread get() = Looper.myLooper() == Looper.getMainLooper()

    private val routerListeners = mutableListOf<RouterListener>()

    /**
     * Sets the [navigator] which will be used to navigate
     */
    override fun setNavigator(navigator: Navigator) {
        requireMainThread()
        this.navigator = navigator
        while (!pendingCommands.isEmpty()) {
            executeCommand(pendingCommands.poll())
        }
    }

    /**
     * Removes the current [Navigator]
     */
    override fun removeNavigator() {
        requireMainThread()
        this.navigator = null
    }

    /**
     * Sends the [commands] to the [Navigator]
     */
    override fun enqueueCommands(vararg commands: Command) = mainThread {
        commands.forEach { command ->
            routerListeners.toList().forEach { it.onCommandEnqueued(command) }
            executeCommand(command)
        }
    }

    /**
     * Adds the [listener]
     */
    override fun addRouterListener(listener: RouterListener) {
        requireMainThread()

        if (!routerListeners.contains(listener)) {
            routerListeners.add(listener)
        }
    }

    /**
     * Removes the previously added [listener]
     */
    override fun removeRouterListener(listener: RouterListener) {
        requireMainThread()
        routerListeners.remove(listener)
    }

    private fun executeCommand(command: Command) {
        val navigator = navigator
        if (navigator != null) {
            routerListeners.toList().forEach { it.preCommandApplied(command) }
            navigator.applyCommand(command)
            routerListeners.toList().forEach { it.postCommandApplied(command) }
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