package com.ivianuu.traveler.lifecycleobserver

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.NavigatorHolder

inline fun NavigatorHolder.setNavigator(
    lifecycleOwner: LifecycleOwner,
    navigator: Navigator
): LifecycleObserver =
    NavigatorLifecycleObserver.start(lifecycleOwner, this, navigator)