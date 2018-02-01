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

package com.ivianuu.traveler.supportfragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.ivianuu.traveler.Navigator
import com.ivianuu.traveler.commands.*
import java.util.*

/**
 * A [Navigator] for [Fragment]'s
 */
abstract class SupportFragmentNavigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : Navigator {
    
    protected val localStackCopy = LinkedList<String>()

    override fun applyCommands(commands: Array<Command>) {
        fragmentManager.executePendingTransactions()

        //copy stack before apply commands
        copyStackToLocal()

        for (command in commands) {
            applyCommand(command)
        }
    }

    protected open fun applyCommand(command: Command) {
        when (command) {
            is Forward -> forward(command)
            is Back -> back()
            is Replace -> replace(command)
            is BackTo -> backTo(command)
            is SystemMessage -> showSystemMessage(command.message)
        }
    }

    protected open fun forward(command: Forward) {
        val fragment = createFragment(command.key)

        if (fragment == null) {
            unknownScreen(command)
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()

        setupFragmentTransactionAnimation(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            fragmentTransaction
        )

        val tag = getFragmentTag(command.key)

        fragmentTransaction
            .replace(containerId, fragment)
            .addToBackStack(tag)
            .commit()
        localStackCopy.add(tag)
    }

    /**
     * Performs [Back] command transition
     */
    protected open fun back() {
        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.pop()
        } else {
            exit()
        }
    }

    /**
     * Performs [Replace] command transition
     */
    protected open fun replace(command: Replace) {
        val fragment = createFragment(command.key)

        if (fragment == null) {
            unknownScreen(command)
            return
        }

        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.pop()

            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransactionAnimation(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
            )

            val tag = getFragmentTag(command.key)

            fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(tag)
                .commit()

            localStackCopy.add(tag)
        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransactionAnimation(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
            )

            fragmentTransaction
                .replace(containerId, fragment)
                .commit()
        }
    }

    /**
     * Performs [BackTo] command transition
     */
    protected open fun backTo(command: BackTo) {
        val key = command.key

        if (key == null) {
            backToRoot()
        } else {
            val index = localStackCopy.indexOf(key)
            val size = localStackCopy.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy.pop()
                }
                fragmentManager.popBackStack(getFragmentTag(key), 0)
            } else {
                backToUnexisting(command)
            }
        }
    }

    protected open fun setupFragmentTransactionAnimation(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        fragmentTransaction: FragmentTransaction
    ) {
    }

    protected open fun backToUnexisting(key: Any) {
        backToRoot()
    }

    protected open fun unknownScreen(command: Command) {
        throw RuntimeException("Can't create a screen for passed screenKey.")
    }

    protected abstract fun createFragment(key: Any): Fragment?

    protected abstract fun getFragmentTag(key: Any): String

    protected abstract fun showSystemMessage(message: String)

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