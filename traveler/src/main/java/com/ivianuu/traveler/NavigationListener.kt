package com.ivianuu.traveler

import com.ivianuu.traveler.commands.Command

/**
 * Navigation listener
 */
interface NavigationListener {

    fun onApplyCommands(commands: Array<out Command>)

}