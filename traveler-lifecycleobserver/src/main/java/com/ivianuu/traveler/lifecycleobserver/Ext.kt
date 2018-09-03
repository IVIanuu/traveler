package com.ivianuu.traveler.lifecycleobserver

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.NavigatorHolder
import com.ivianuu.traveler.commands.Command

inline fun NavigatorHolder.setNavigator(
    lifecycleOwner: LifecycleOwner,
    navigator: Navigator
): LifecycleObserver =
    NavigatorLifecycleObserver.start(lifecycleOwner, this, navigator)

fun NavigatorHolder.setNavigator(
    lifecycleOwner: LifecycleOwner,
    applyCommand: (Command) -> Unit
): LifecycleObserver =
    NavigatorLifecycleObserver.start(lifecycleOwner, this, object : Navigator {
        override fun applyCommand(command: Command) {
            applyCommand.invoke(command)
        }
    })