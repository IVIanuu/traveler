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

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import kotlin.reflect.KClass

open class OpenDetourPilot : CompassDetourPilot {

    private val reifiedDetours = mutableListOf<ReifiedDetour<*, *, *>>()

    override fun <Destination : Any,
            CurrentFragment : Fragment,
            NextFragment : Fragment> registerDetour(detour: CompassDetour<Destination,
            CurrentFragment, NextFragment>, destinationClass: KClass<Destination>,
                                                    currentFragmentClass: KClass<CurrentFragment>,
                                                    nextFragmentClass: KClass<NextFragment>) {
        reifiedDetours.add(ReifiedDetour(detour, destinationClass, currentFragmentClass, nextFragmentClass))
    }

    override fun setup(destination: Any,
                       currentFragment: Fragment?,
                       nextFragment: Fragment,
                       transaction: FragmentTransaction) {
        reifiedDetours
            .firstOrNull { it.trySetup(destination, currentFragment, nextFragment, transaction) }
    }


    private data class ReifiedDetour<Destination : Any, CurrentFragment : Any, NextFragment : Any>
        (private val detour: CompassDetour<Destination, CurrentFragment, NextFragment>,
         private val destinationClass: KClass<Destination>,
         private val currentFragmentClass: KClass<CurrentFragment>,
         private val nextFragmentClass: KClass<NextFragment>) {
        fun trySetup(destination: Any,
                     currentFragment: Fragment?,
                     nextFragment: Fragment,
                     transaction: FragmentTransaction): Boolean {
            if (destinationClass.java.isInstance(destination)
                && currentFragmentClass.java.isInstance(currentFragment)
                && nextFragmentClass.java.isInstance(nextFragment)) {

                @Suppress("UNCHECKED_CAST")
                detour.setup(destination as Destination,
                    currentFragment as CurrentFragment,
                    nextFragment as NextFragment,
                    transaction)

                return true
            }

            return false
        }
    }
}