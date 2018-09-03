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

package com.ivianuu.traveler.sample.conductor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.ivianuu.traveler.commands.Command
import com.ivianuu.traveler.conductorfork.ControllerNavigator
import com.ivianuu.traveler.lifecycleobserver.setNavigator
import com.ivianuu.traveler.sample.getTraveler
import com.ivianuu.traveler.sample.widget.CounterKey

class ConductorActivity : AppCompatActivity() {

    private val traveler get() = getTraveler("conductor")

    private lateinit var router: Router

    private val controllerNavigator by lazy(LazyThreadSafetyMode.NONE) {
        object : ControllerNavigator(router) {
            override fun createController(key: Any, data: Any?): Controller? {
                return CounterController(
                    Bundle().apply {
                        putParcelable("key", key as CounterKey)
                    }
                )
            }

            override fun exit() {
                finish()
            }

            override fun setupRouterTransaction(command: Command, transaction: RouterTransaction) {
                transaction.pushChangeHandler(VerticalChangeHandler())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Conductor.attachRouter(this,
            findViewById(android.R.id.content), savedInstanceState)

        traveler.navigatorHolder.setNavigator(this, controllerNavigator)

        if (savedInstanceState == null) {
            traveler.router.setRoot(CounterKey(1))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}