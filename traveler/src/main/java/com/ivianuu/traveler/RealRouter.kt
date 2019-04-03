package com.ivianuu.traveler

import android.os.Handler
import android.os.Looper
import com.ivianuu.closeable.Closeable
import java.util.*

/**
 * The actual implementation of a [Router]
 */
open class RealRouter : Router {

    override val navigator: Navigator?
        get() = _navigator
    private var _navigator: Navigator? = null
    private val pendingCommands = LinkedList<Command>()

    private val handler = Handler(Looper.getMainLooper())
    private val isMainThread get() = Looper.myLooper() == Looper.getMainLooper()

    private val listeners = mutableSetOf<RouterListener>()

    override fun setNavigator(navigator: Navigator): Closeable {
        requireMainThread()
        if (_navigator != navigator) {
            _navigator = navigator
            notifyListeners { it.onNavigatorSet(this, navigator) }
            executePendingCommands()
        }

        return Closeable(this::removeNavigator)
    }

    override fun removeNavigator() {
        requireMainThread()
        _navigator?.let { navigator ->
            notifyListeners { it.onNavigatorRemoved(this, navigator) }
        }
        _navigator = null
    }

    override fun enqueueCommands(vararg commands: Command) = mainThread {
        commands.forEach { command ->
            notifyListeners { it.onCommandEnqueued(this, command) }
            executeCommand(command)
        }
    }

    override fun addListener(listener: RouterListener): Closeable {
        requireMainThread()
        listeners.add(listener)
        return Closeable { removeListener(listener) }
    }

    override fun removeListener(listener: RouterListener) {
        requireMainThread()
        listeners.remove(listener)
    }

    private fun executePendingCommands() {
        while (!pendingCommands.isEmpty()) {
            executeCommand(pendingCommands.poll())
        }
    }

    private fun executeCommand(command: Command) {
        val navigator = _navigator
        if (navigator != null) {
            notifyListeners { it.preCommandApplied(this, navigator, command) }
            navigator.applyCommand(command)
            notifyListeners { it.postCommandApplied(this, navigator, command) }
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

    private inline fun notifyListeners(block: (RouterListener) -> Unit) {
        listeners.toList().forEach(block)
    }
}