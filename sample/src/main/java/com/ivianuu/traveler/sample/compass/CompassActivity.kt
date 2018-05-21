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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ivianuu.traveler.lifecycleobserver.NavigatorLifecycleObserver
import com.ivianuu.traveler.sample.*

/**
 * @author Manuel Wrage (IVIanuu)
 */
class CompassActivity : AppCompatActivity() {

    private val navigator by lazy(LazyThreadSafetyMode.NONE) {
        compassNavigatorBuilder(android.R.id.content)
            .autoMap()
            .autoCrane()
            .autoPilot()
            .build()
    }

    private val traveler get() = getTraveler("compass")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NavigatorLifecycleObserver.start(this, navigator, traveler.navigatorHolder)

        if (savedInstanceState == null) {
            traveler.router.newRootScreen(CounterDestination(1))
        }
    }

}