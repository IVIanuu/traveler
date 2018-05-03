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

package com.ivianuu.traveler.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivianuu.traveler.Router
import com.ivianuu.traveler.keys.FragmentClassKey
import com.ivianuu.traveler.keys.requireKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_counter.*

@Parcelize
data class CounterKey(val count: Int) : FragmentClassKey(CounterFragment::class), Parcelable

class CounterFragment : Fragment() {

    private val router: Router
        get() = (activity as MainActivity).traveler.router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = requireKey<CounterKey>()

        title.text = "Count ${key.count}"

        add.setOnClickListener { router.navigateTo(CounterKey(key.count + 1)) }
        remove.setOnClickListener { router.exit() }
        show_message.setOnClickListener { router.showSystemMessage(R.string.test_string, key.count) }
        show_dialog.setOnClickListener { router.navigateTo(CountDialogKey(key.count)) }
        new_activity.setOnClickListener { router.navigateTo(MainKey) }
        root.setOnClickListener { router.backToRoot() }
        quit.setOnClickListener { router.finishChain() }
    }
}