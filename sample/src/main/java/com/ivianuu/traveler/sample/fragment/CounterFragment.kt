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
import androidx.fragment.app.Fragment
import com.ivianuu.traveler.*
import com.ivianuu.traveler.fragment.FragmentKey
import com.ivianuu.traveler.sample.R
import com.ivianuu.traveler.sample.router
import com.ivianuu.traveler.sample.showToast
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.view_counter.*

@Parcelize
data class CounterKey(val count: Int) : FragmentKey, Parcelable {
    override fun createFragment(data: Any?): Fragment {
        return CounterFragment().apply {
            arguments = Bundle().apply {
                putParcelable("key", this@CounterKey)
            }
        }
    }
}

class CounterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.view_counter, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key = arguments!!.getParcelable<CounterKey>("key")!!

        val router = router("fragments")

        title.text = "Count ${key.count}"

        add.setOnClickListener { router.navigate(CounterKey(key.count + 1)) }
        remove.setOnClickListener { router.goBack() }
        root.setOnClickListener { router.popToRoot() }
        pop_to_3.setOnClickListener { router.popTo(CounterKey(3)) }
        quit.setOnClickListener { router.finish() }
        toast.setOnClickListener { router.showToast("Hello :)") }
    }
}