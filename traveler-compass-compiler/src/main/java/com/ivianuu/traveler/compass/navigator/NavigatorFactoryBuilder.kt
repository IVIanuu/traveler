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

package com.ivianuu.traveler.compass.navigator

import com.squareup.kotlinpoet.*
import javax.annotation.processing.ProcessingEnvironment

object NavigatorFactoryBuilder {

    private val CLASS_COMPASS_CRANE =
        ClassName.bestGuess("com.ivianuu.traveler.compass.CompassCrane")
    private val CLASS_COMPASS_MAP =
        ClassName.bestGuess("com.ivianuu.traveler.compass.CompassMap")
    private val CLASS_COMPASS_NAVIGATOR =
        ClassName.bestGuess("com.ivianuu.traveler.compass.CompassNavigator")
    private val CLASS_COMPASS_NAVIGATOR_BUILDER =
        ClassName.bestGuess("com.ivianuu.traveler.compass.CompassNavigator.Builder")
    private val CLASS_FRAGMENT_ACTIVITY =
        ClassName.bestGuess("android.support.v4.app.FragmentActivity")
    private val CLASS_FRAGMENT = ClassName.bestGuess("android.support.v4.app.Fragment")

    fun buildNavigatorExtensions(
        environment: ProcessingEnvironment,
        builder: FileSpec.Builder
    ) {
        /*val activityNavigatorFunction = buildNavigatorFunction(
            CLASS_FRAGMENT_ACTIVITY, "this", "supportFragmentManager"
        )
        builder.addFunction(activityNavigatorFunction)*/

        val activityNavigatorBuilderFunction = buildNavigatorBuilderFunction(
            CLASS_FRAGMENT_ACTIVITY, "this", "supportFragmentManager"
        )
        builder.addFunction(activityNavigatorBuilderFunction)

        /*val fragmentNavigatorFunction = buildNavigatorFunction(
            CLASS_FRAGMENT, "requireActivity()", "childFragmentManager")
        builder.addFunction(fragmentNavigatorFunction)*/

        val fragmentNavigatorBuilderFunction = buildNavigatorBuilderFunction(
            CLASS_FRAGMENT, "requireActivity()", "childFragmentManager")
        builder.addFunction(fragmentNavigatorBuilderFunction)
    }

    private fun buildNavigatorFunction(
        receiver: TypeName,
        activity: String,
        fm: String
    ): FunSpec {
        return FunSpec.builder("compassNavigator")
            .receiver(receiver)
            .returns(CLASS_COMPASS_NAVIGATOR)
            .addParameter("containerId", Int::class)
            .addCode(
                CodeBlock.builder()
                    .addStatement(
                        "return %T.builder($activity, $fm, containerId)" +
                                ".autoMap()" +
                                ".autoCrane()" +
                                ".autoPilot()" +
                                ".build()",
                        CLASS_COMPASS_NAVIGATOR
                    )
                    .build()
            )
            .build()
    }

    private fun buildNavigatorBuilderFunction(
        receiver: TypeName,
        activity: String,
        fm: String
    ): FunSpec {
        return FunSpec.builder("compassNavigatorBuilder")
            .receiver(receiver)
            .returns(CLASS_COMPASS_NAVIGATOR_BUILDER)
            .addParameter("containerId", Int::class)
            .addCode(
                CodeBlock.builder()
                    .addStatement(
                        "return %T.builder($activity, $fm, containerId)",
                            //    ".autoMap()" +
                            //    ".autoCrane()" +
                            //    ".autoPilot()",
                        CLASS_COMPASS_NAVIGATOR
                    )
                    .build()
            )
            .build()
    }
}