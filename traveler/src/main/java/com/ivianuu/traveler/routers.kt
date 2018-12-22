package com.ivianuu.traveler

/**
 * Sends the [commands] to the [Navigator]
 */
fun Router.enqueueCommands(commands: Collection<Command>) {
    enqueueCommands(*commands.toTypedArray())
}

/**
 * Navigates forward to [key]
 */
fun Router.navigate(key: Any, data: Any? = null) {
    enqueueCommands(Forward(key, data))
}

/**
 * Clears all screen and opens [key]
 */
fun Router.setRoot(key: Any, data: Any? = null) {
    enqueueCommands(
        BackTo(null),
        Replace(key, data)
    )
}

/**
 * Replaces the top screen with [key]
 */
fun Router.replaceTop(key: Any, data: Any? = null) {
    enqueueCommands(Replace(key, data))
}

/**
 * Opens all [keys]
 */
fun Router.newChain(vararg keys: Any) {
    val commands =
        arrayOf(BackTo(null), *keys.map { Forward(it, null) }.toTypedArray())
    enqueueCommands(*commands)
}

/**
 * Pops to the root and opens all [keys]
 */
fun Router.newRootChain(vararg keys: Any) {
    val commands =
        arrayOf(BackTo(null), *keys.map { Forward(it, null) }.toTypedArray())
    enqueueCommands(*commands)
}

/**
 * Goes back to [key]
 */
fun Router.popTo(key: Any) {
    enqueueCommands(BackTo(key))
}

/**
 * Goes back to the root screen
 */
fun Router.popToRoot() {
    enqueueCommands(BackTo(null))
}

/**
 * Goes back to the previous screen
 */
fun Router.goBack() {
    enqueueCommands(Back())
}

/**
 * Finishes the chain
 */
fun Router.finish() {
    enqueueCommands(
        BackTo(null),
        Back()
    )
}