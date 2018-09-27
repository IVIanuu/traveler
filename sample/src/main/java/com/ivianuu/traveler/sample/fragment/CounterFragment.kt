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

package com.ivianuu.traveler.sample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivianuu.traveler.sample.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CounterKey(val count: Int) : Parcelable

class CounterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_counter, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key = arguments!!.getParcelable<CounterKey>("key")!!

        val router = traveler("fragments").router

        title.text = "Count ${key.count}"

        add.setOnClickListener { router.navigate(CounterKey(key.count + 1)) }
        remove.setOnClickListener { router.goBack() }
        root.setOnClickListener { router.popToRoot() }
        pop_to_3.setOnClickListener { router.popTo(CounterKey(3)) }
        quit.setOnClickListener { router.finish() }
        toast.setOnClickListener { router.showToast("Hello :)") }
    }
}