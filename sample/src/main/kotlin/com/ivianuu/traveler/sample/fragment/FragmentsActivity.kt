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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.traveler.android.ActivityKey
import com.ivianuu.traveler.android.FragmentNavigator
import com.ivianuu.traveler.android.setNavigator
import com.ivianuu.traveler.common.compositeNavigatorOf
import com.ivianuu.traveler.sample.ToastNavigator
import com.ivianuu.traveler.sample.router
import com.ivianuu.traveler.setRoot

class FragmentsKey : ActivityKey {
    override fun createIntent(context: Context, data: Any?) =
        Intent(context, FragmentsActivity::class.java)
}

class FragmentsActivity : AppCompatActivity() {

    private val router by lazy { router("fragments") }

    private val navigator by lazy {
        compositeNavigatorOf(
            FragmentNavigator(
                supportFragmentManager,
                android.R.id.content
            ),
            ToastNavigator(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            router.setRoot(CounterKey(1))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        router.setNavigator(this, navigator)
    }

}