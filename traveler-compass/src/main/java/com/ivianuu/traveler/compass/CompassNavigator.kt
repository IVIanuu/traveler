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
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.ivianuu.traveler.commands.Command
import com.ivianuu.traveler.fragments.FragmentAppNavigator

/**
 * Compass navigator
 */
class CompassNavigator(
    activity: Activity,
    fragmentManager: FragmentManager,
    containerId: Int,
    private val maps: List<CompassMap>,
    private val cranes: List<CompassCrane>,
    private val pilot: CompassDetour<Any, Fragment, Fragment>
) : FragmentAppNavigator(activity, fragmentManager, containerId) {

    override fun createFragment(key: Any, data: Any?): Fragment? {
        val route = maps.map { it.get(key) }.firstOrNull()
        return if (route is FragmentRoute) {
            val fragment = route.fragment

            val bundle = cranes.map { it.toBundle(key) }.firstOrNull()

            if (bundle != null) {
                fragment.arguments = bundle
            }

            fragment
        } else {
            null
        }
    }

    override fun createActivityIntent(context: Context, key: Any, data: Any?): Intent? {
        val route = maps.map { it.get(key) }.firstOrNull()
        return when (route) {
            is ActivityRoute -> {
                val intent = Intent(context, route.activityClass.java)

                val bundle = cranes.map { it.toBundle(key) }.firstOrNull()

                if (bundle != null) {
                    intent.putExtras(bundle)
                }

                intent
            }
            else -> null
        }
    }

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        transaction: FragmentTransaction
    ) {
        pilot.setup(command, currentFragment, nextFragment, transaction)
    }

    class Builder internal constructor(
        private val activity: FragmentActivity,
        private val fm: FragmentManager,
        private val containerId: Int
    ) {

        private val maps = mutableListOf<CompassMap>()
        private val cranes = mutableListOf<CompassCrane>()
        private val pilots = mutableListOf<CompassDetourPilot>()

        @PublishedApi
        internal val detourPilot = CompassDetourPilot.create()

        fun addMap(map: CompassMap): Builder {
            maps.add(map)
            return this
        }

        fun addCrane(crane: CompassCrane): Builder {
            cranes.add(crane)
            return this
        }

        fun addPilot(vararg pilots: CompassDetourPilot): Builder {
            this.pilots.addAll(pilots)
            return this
        }

        inline fun <reified Destination : Any,
                reified CurrentFragment : Fragment,
                reified NextFragment : Fragment>
                addDetour(detour: CompassDetour<Destination, CurrentFragment, NextFragment>): Builder {
            detourPilot.registerDetour(detour)
            return this
        }

        fun build(): CompassNavigator {
            return CompassNavigator(
                activity,
                fm,
                containerId,
                maps,
                cranes,
                object : OpenDetourPilot() {
                    override fun setup(
                        destination: Any,
                        currentFragment: Fragment?,
                        nextFragment: Fragment,
                        transaction: FragmentTransaction
                    ) {
                        pilots.forEach { pilot ->
                            pilot.setup(destination, currentFragment, nextFragment, transaction)
                        }
                    }
                }
            )
        }
    }

    companion object {

        fun builder(
            activity: FragmentActivity,
            fm: FragmentManager,
            containerId: Int
        ): Builder = Builder(activity, fm, containerId)

    }
}