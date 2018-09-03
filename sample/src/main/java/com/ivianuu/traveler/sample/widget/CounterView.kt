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

package com.ivianuu.traveler.sample.widget

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ivianuu.traveler.Router

import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.view_counter.view.*

@Parcelize
data class CounterKey(val count: Int) : Parcelable

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    fun init(key: CounterKey, router: Router) {
        title.text = "Count ${key.count}"

        add.setOnClickListener { router.navigateTo(CounterKey(key.count + 1)) }
        remove.setOnClickListener { router.finish() }
        root.setOnClickListener { router.popToRoot() }
        quit.setOnClickListener { router.finish() }
    }

}