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
import com.ivianuu.traveler.BaseNavigator
import com.ivianuu.traveler.commands.*
import java.util.*

/**
 * Navigator for fragments only
 */
abstract class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
): BaseNavigator() {

    private val localStackCopy = LinkedList<String>()

    override fun applyCommands(commands: Array<Command>) {
        try {
            fragmentManager.executePendingTransactions()
        } catch (e: Exception) {
            // ignore
        }

        //copy stack before apply commands
        copyStackToLocal()

        super.applyCommands(commands)
    }

    override fun forward(command: Forward) {
        val fragment = createFragment(command.key, command.data)

        if (fragment == null) {
            unknownScreen(command)
            return
        }

        val tag = getFragmentTag(command.key)

        if (tag == null) {
            unknownScreen(command)
            return
        }

        val transaction = fragmentManager.beginTransaction()

        setupFragmentTransaction(
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
    }

    override fun replace(command: Replace) {
        val fragment = createFragment(command.key, command.data)

        if (fragment == null) {
            unknownScreen(command)
            return
        }

        val tag = getFragmentTag(command.key)

        if (tag == null) {
            unknownScreen(command)
            return
        }

        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.pop()

            val transaction = fragmentManager.beginTransaction()

            setupFragmentTransaction(
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

            setupFragmentTransaction(
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
    }

    override fun back(command: Back) {
        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.pop()
        } else {
            exit()
        }
    }

    override fun backTo(command: BackTo) {
        val key = command.key

        if (key == null) {
            backToRoot()
        } else {
            val tag = getFragmentTag(key)

            if (tag == null) {
                unknownScreen(command)
                return
            }

            val index = localStackCopy.indexOf(tag)
            val size = localStackCopy.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy.pop()
                }
                fragmentManager.popBackStack(tag, 0)
            } else {
                backToUnexisting(command)
            }
        }
    }

    open fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        transaction: FragmentTransaction
    ) {
    }

    protected open fun backToUnexisting(key: Any) {
        backToRoot()
    }

    protected open fun unknownScreen(command: Command) {
        throw IllegalArgumentException("unknown screen $command")
    }

    protected open fun getFragmentTag(key: Any): String? {
        return key.toString()
    }

    protected abstract fun createFragment(key: Any, data: Any?): Fragment?

    protected abstract fun exit()

    private fun copyStackToLocal() {
        localStackCopy.clear()

        val stackSize = fragmentManager.backStackEntryCount
        (0 until stackSize).mapTo(localStackCopy) { fragmentManager.getBackStackEntryAt(it).name }
    }

    private fun backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy.clear()
    }
}