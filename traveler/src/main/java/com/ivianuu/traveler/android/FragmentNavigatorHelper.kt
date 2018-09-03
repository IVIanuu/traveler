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

package com.ivianuu.traveler.android

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.ivianuu.traveler.commands.*
import java.util.*

/**
 * Helper for implementing a navigator for fragments
 */
class FragmentNavigatorHelper(
    private val callback: Callback,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) {

    private val localStackCopy = LinkedList<String>()

    fun forward(command: Forward): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        val fragment = callback.createFragment(command.key, command.data) ?: return false

        val tag = callback.getFragmentTag(command.key)

        val transaction = fragmentManager.beginTransaction()

        callback.setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            transaction
        )

        if (fragment !is DialogFragment) {
            transaction
                .replace(containerId, fragment, tag)
                .addToBackStack(tag)
                .commit()
        } else {
            transaction.addToBackStack(tag)
            fragment.show(transaction, tag)
        }

        localStackCopy.add(tag)

        return true
    }

    fun replace(command: Replace): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        val fragment = callback.createFragment(command.key, command.data) ?: return false

        val tag = callback.getFragmentTag(command.key)

        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.pop()

            val transaction = fragmentManager.beginTransaction()

            callback.setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                transaction
            )

            if (fragment !is DialogFragment) {
                transaction
                    .replace(containerId, fragment, tag)
                    .addToBackStack(tag)
                    .commit()
            } else {
                transaction.addToBackStack(tag)
                fragment.show(transaction, tag)
            }

            localStackCopy.add(tag)
        } else {
            val transaction = fragmentManager.beginTransaction()

            callback.setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                transaction
            )

            if (fragment !is DialogFragment) {
                transaction
                    .replace(containerId, fragment, tag)
                    .commit()
            } else {
                transaction.addToBackStack(tag)
                fragment.show(transaction, tag)
            }
        }

        return true
    }

    fun back(command: Back): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        return if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.pop()
            true
        } else {
            false
        }
    }

    fun backTo(command: BackTo): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        val key = command.key

        return if (key == null) {
            backToRoot()
            true
        } else {
            val tag = callback.getFragmentTag(key)

            val index = localStackCopy.indexOf(tag)
            val size = localStackCopy.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy.pop()
                }
                fragmentManager.popBackStack(tag, 0)
                true
            } else {
                false
            }
        }
    }

    private fun executePendingTransactions() {
        try {
            fragmentManager.executePendingTransactions()
        } catch (e: Exception) {
            // ignore
        }
    }

    private fun copyStackToLocal() {
        localStackCopy.clear()

        (0 until fragmentManager.backStackEntryCount)
            .map(fragmentManager::getBackStackEntryAt)
            .mapNotNull(FragmentManager.BackStackEntry::getName)
            .forEach { localStackCopy.add(it) }
    }

    fun backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy.clear()
    }

    interface Callback {

        fun createFragment(key: Any, data: Any?): Fragment?

        fun getFragmentTag(key: Any) = key.toString()

        fun setupFragmentTransaction(
            command: Command,
            currentFragment: Fragment?,
            nextFragment: Fragment,
            transaction: FragmentTransaction
        ) {
        }
    }
}