package com.ivianuu.traveler

import java.util.*

/**
 * The actual implementation of a [Router]
 */
open class RealRouter : Router {

    override val navigator: Navigator?
        get() = _navigator
    private var _navigator: Navigator? = null
    private val pendingCommands = LinkedList<Command>()

    private val listeners = mutableSetOf<RouterListener>()

    override fun setNavigator(navigator: Navigator) {
        if (_navigator != navigator) {
            _navigator = navigator
            notifyListeners { it.onNavigatorSet(this, navigator) }
            executePendingCommands()
        }
    }

    override fun removeNavigator() {
        _navigator?.let { navigator ->
            notifyListeners { it.onNavigatorRemoved(this, navigator) }
        }
        _navigator = null
    }

    override fun enqueueCommands(vararg commands: Command) {
        commands.forEach { command ->
            notifyListeners { it.onCommandEnqueued(this, command) }
            executeCommand(command)
        }
    }

    override fun addListener(listener: RouterListener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: RouterListener) {
        listeners.remove(listener)
    }

    protected open fun executePendingCommands() {
        while (!pendingCommands.isEmpty()) {
            executeCommand(pendingCommands.poll())
        }
    }

    protected open fun executeCommand(command: Command) {
        val navigator = _navigator
        if (navigator != null) {
            notifyListeners { it.preCommandApplied(this, navigator, command) }
            navigator.applyCommand(command)
            notifyListeners { it.postCommandApplied(this, navigator, command) }
        } else {
            pendingCommands.add(command)
        }
    }

    private inline fun notifyListeners(block: (RouterListener) -> Unit) {
        listeners.toList().forEach(block)
    }
}