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

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ivianuu.traveler.Back
import com.ivianuu.traveler.BackTo
import com.ivianuu.traveler.Command
import com.ivianuu.traveler.Forward
import com.ivianuu.traveler.MetaCommand
import com.ivianuu.traveler.Replace
import com.ivianuu.traveler.common.ResultNavigator
import java.util.*

/**
 * Navigator for fragments only
 */
open class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : ResultNavigator() {

    private val localStack = LinkedList<String>()

    override fun applyCommandWithResult(command: Command): Boolean {
        return when (command) {
            is Forward -> forward(command)
            is Replace -> replace(command)
            is Back -> back(command)
            is BackTo -> backTo(command)
            else -> unsupportedCommand(command)
        }
    }

    /**
     * Navigates forward
     */
    protected open fun forward(command: Forward): Boolean {
        executePendingTransactions()
        updateLocalStack()

        val fragment = createFragment(command.key, command.data)
            ?: return unknownScreen(command.key)

        val tag = getFragmentTag(command.key)

        val transaction = fragmentManager.beginTransaction()
            .setReorderingAllowed(true)

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

        localStack.add(tag)
        return true
    }

    /**
     * Replaces the current screen with the new one
     */
    protected open fun replace(command: Replace): Boolean {
        executePendingTransactions()
        updateLocalStack()

        val fragment = createFragment(command.key, command.data)
            ?: return unknownScreen(command.key)

        val tag = getFragmentTag(command.key)

        if (localStack.size > 0) {
            fragmentManager.popBackStack()
            localStack.removeLast()

            val transaction = fragmentManager.beginTransaction()
                .setReorderingAllowed(true)

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

            localStack.add(tag)
        } else {
            val transaction = fragmentManager.beginTransaction()
                .setReorderingAllowed(true)

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

    /**
     * Navigates back
     */
    protected open fun back(command: Back): Boolean {
        executePendingTransactions()
        updateLocalStack()

        return if (localStack.size > 0) {
            fragmentManager.popBackStack()
            localStack.removeLast()
            true
        } else {
            exit()
        }
    }

    /**
     * Navigates back to [BackTo.key] or to the root
     */
    protected open fun backTo(command: BackTo): Boolean {
        executePendingTransactions()
        updateLocalStack()

        val key = command.key

        return if (key == null) {
            backToRoot()
        } else {
            val tag = getFragmentTag(key)

            val index = localStack.indexOf(tag)
            val size = localStack.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStack.removeLast()
                }
                fragmentManager.popBackStack(tag, 0)
                true
            } else {
                backToUnexisting(key)
            }
        }
    }

    /**
     * Navigates back to root
     */
    protected open fun backToRoot(): Boolean {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStack.clear()
        return true
    }

    /**
     * Will be called on a [backTo] with a unknown key
     */
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

    private fun updateLocalStack() {
        localStack.clear()

        (0 until fragmentManager.backStackEntryCount)
            .map { fragmentManager.getBackStackEntryAt(it) }
            .mapNotNull { it.getName() }
            .forEach { localStack.add(it) }
    }
}