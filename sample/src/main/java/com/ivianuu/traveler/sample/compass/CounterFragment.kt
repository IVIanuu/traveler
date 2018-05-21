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

package com.ivianuu.traveler.sample.compass

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivianuu.traveler.compass.CompassDetour
import com.ivianuu.traveler.compass.Destination
import com.ivianuu.traveler.compass.Detour
import com.ivianuu.traveler.sample.R
import com.ivianuu.traveler.sample.getTraveler
import kotlinx.android.synthetic.main.view_counter.*

@Destination(CounterFragment::class)
data class CounterDestination(val count: Int)

@Destination(MyOtherFragment::class)
data class MyOtherDestination(val hehe: List<String>)

class MyOtherFragment : Fragment()

@Detour
class TestDetour : CompassDetour<Any, Fragment, Fragment> {
    override fun setup(
        destination: Any,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        transaction: FragmentTransaction
    ) {
        currentFragment?.exitTransition = Slide(Gravity.START)
        nextFragment.enterTransition = Slide(Gravity.END)
    }
}

class CounterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_counter, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val router = requireActivity().getTraveler("compass").router

        val dest = counterDestination()

        title.text = "Count ${dest.count}"

        add.setOnClickListener { router.navigateTo(CounterDestination(dest.count + 1)) }
        remove.setOnClickListener { router.exit() }
        root.setOnClickListener { router.backToRoot() }
        quit.setOnClickListener { router.finishChain() }
    }
}