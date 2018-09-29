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

package com.ivianuu.traveler.sample.director

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.director.Controller
import com.ivianuu.director.Router
import com.ivianuu.director.attachRouter
import com.ivianuu.traveler.common.compositeNavigatorOf
import com.ivianuu.traveler.director.ControllerNavigator
import com.ivianuu.traveler.lifecycle.setNavigator
import com.ivianuu.traveler.sample.ToastNavigator
import com.ivianuu.traveler.sample.traveler
import com.ivianuu.traveler.setRoot

private class CounterNavigator(
    private val activity: Activity,
    router: Router
) : ControllerNavigator(router) {

    override fun createController(key: Any, data: Any?): Controller? {
        return CounterController().apply {
            args = Bundle().apply {
                putParcelable("key", key as CounterKey)
            }
        }
    }

    override fun exit(): Boolean {
        activity.finish()
        return true
    }
}

class DirectorActivity : AppCompatActivity() {

    private val traveler by lazy { traveler("director") }
    private val navigatorHolder get() = traveler.navigatorHolder
    private val router get() = traveler.router

    private val navigator by lazy {
        compositeNavigatorOf(
            CounterNavigator(this, directorRouter),
            ToastNavigator(this)
        )
    }

    private lateinit var directorRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        directorRouter = attachRouter(findViewById(android.R.id.content), savedInstanceState)

        if (!directorRouter.hasRootController) {
            router.setRoot(CounterKey(1))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(this, navigator)
    }

    override fun onBackPressed() {
        if (!directorRouter.handleBack()) {
            super.onBackPressed()
        }
    }
}