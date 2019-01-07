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

package com.ivianuu.traveler.fragment

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ivianuu.traveler.*
import com.ivianuu.traveler.common.ResultNavigator
import java.util.*

/**
 * Navigator for fragments only
 */
open class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : ResultNavigator() {

    private val localStackCopy = LinkedList<String>()

    override fun applyCommandWithResult(command: Command): Boolean {
        return when (command) {
            is Forward -> forward(command)
            is Replace -> replace(command)
            is Back -> back(command)
            is BackTo -> backTo(command)
            else -> unsupportedCommand(command)
        }
    }

    protected open fun forward(command: Forward): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        val fragment = createFragment(command.key, command.data)
            ?: return unknownScreen(command.key)

        val tag = getFragmentTag(command.key)

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
        return true
    }

    protected open fun replace(command: Replace): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        val fragment = createFragment(command.key, command.data)
            ?: return unknownScreen(command.key)

        val tag = getFragmentTag(command.key)

        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.removeLast()

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

        return true
    }

    protected open fun back(command: Back): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        return if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.removeLast()
            true
        } else {
            exit()
        }
    }

    protected open fun backTo(command: BackTo): Boolean {
        executePendingTransactions()
        copyStackToLocal()

        val key = command.key

        return if (key == null) {
            backToRoot()
        } else {
            val tag = getFragmentTag(key)

            val index = localStackCopy.indexOf(tag)
            val size = localStackCopy.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy.removeLast()
                }
                fragmentManager.popBackStack(tag, 0)
                true
            } else {
                backToUnexisting(key)
            }
        }
    }

    protected open fun backToRoot(): Boolean {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy.clear()
        return true
    }

    protected open fun backToUnexisting(key: Any): Boolean {
        backToRoot()
        return true
    }

    /**
     * Will be called when the backstack is empty and the hosting activity should be closed
     * This is a no op by default
     */
    protected open fun exit(): Boolean = true

    /**
     * Creates the corresponding [Fragment] for [key]
     */
    protected open fun createFragment(key: Any, data: Any?): Fragment? {
        return when (key) {
            is FragmentKey -> key.createFragment(data)
            else -> null
        }
    }

    /**
     * Returns the corresponding fragment tag for [key]
     */
    protected open fun getFragmentTag(key: Any) = when (key) {
        is FragmentKey -> key.getFragmentTag()
        else -> key.toString()
    }

    /**
     * Setup the transaction e.g. animations, shared elements
     */
    protected open fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        transaction: FragmentTransaction
    ) {
        ((command as? MetaCommand)?.key as? FragmentKey)
            ?.setupFragmentTransaction(command, currentFragment, nextFragment, transaction)
    }

    /**
     * Will be called when a unknown screen was requested
     */
    protected open fun unknownScreen(key: Any) = false

    /**
     * Will be called when a unsupported command was send
     */
    protected open fun unsupportedCommand(command: Command) = false

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
}