/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.traveler.compass

import android.app.Activity
import android.support.v4.app.Fragment
import kotlin.reflect.KClass

interface CompassRoute

internal class ActivityRoute(val activityClass: KClass<out Activity>) : CompassRoute

internal class FragmentRoute(val fragment: Fragment) : CompassRoute

fun <T : Activity> route(activityClass: KClass<T>): CompassRoute = ActivityRoute(activityClass)

fun route(fragment: Fragment): CompassRoute = FragmentRoute(fragment)

@JvmName("asActivityRoute")
fun <T : Activity> KClass<T>.asRoute(): CompassRoute = route(this)

@JvmName("asFragmentRoute")
fun <T : Fragment> KClass<T>.asRoute(): CompassRoute = route(this.java.newInstance())

fun Fragment.asRoute(): CompassRoute = route(this)