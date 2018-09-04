package com.ivianuu.traveler

import com.ivianuu.traveler.command.Command

/**
 * Navigation listener
 */
interface NavigationListener {

    fun onApplyCommands(commands: Array<out Command>)

}