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

package com.ivianuu.traveler.compass.util

import android.os.Parcelable
import java.util.*

object TypeUtil {

    fun toIntArray(list: List<Int>?): IntArray {
        if (list == null) return IntArray(0)
        val array = IntArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }
        return array
    }

    fun toIntegerList(array: IntArray): List<Int> {
        val list = ArrayList<Int>(array.size)
        for (i in array.indices) {
            list.add(i, array[i])
        }

        return list
    }

    fun toFloatArray(list: List<Float>?): FloatArray {
        if (list == null) return FloatArray(0)
        val array = FloatArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }

        return array
    }

    fun toFloatList(array: FloatArray): List<Float> {
        val list = ArrayList<Float>(array.size)
        for (f in array) {
            list.add(f)
        }

        return list
    }

    fun toDoubleArray(list: List<Double>?): DoubleArray {
        if (list == null) return DoubleArray(0)
        val array = DoubleArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }

        return array
    }

    fun toDoubleList(array: DoubleArray?): List<Double> {
        if (array == null) return ArrayList()
        val list = ArrayList<Double>()
        for (d in array) {
            list.add(d)
        }
        return list
    }

    fun toCharArray(list: List<Char>?): CharArray {
        if (list == null) return CharArray(0)
        val array = CharArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }

        return array
    }

    fun toCharacterList(array: CharArray?): List<Char> {
        if (array == null) return ArrayList()
        val list = ArrayList<Char>(array.size)
        for (c in array) {
            list.add(c)
        }

        return list
    }

    fun toBooleanArray(list: List<Boolean>?): BooleanArray {
        if (list == null) return BooleanArray(0)
        val array = BooleanArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }
        return array
    }

    fun toBooleanList(array: BooleanArray?): List<Boolean> {
        if (array == null) return ArrayList()
        val list = ArrayList<Boolean>(array.size)
        for (b in array) {
            list.add(b)
        }

        return list
    }

    fun toLongArray(list: List<Long>?): LongArray {
        if (list == null) return LongArray(0)
        val array = LongArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }

        return array
    }

    fun toLongList(array: LongArray?): List<Long> {
        if (array == null) return ArrayList()
        val list = ArrayList<Long>(array.size)
        for (l in array) {
            list.add(l)
        }

        return list
    }

    fun toShortArray(list: List<Short>?): ShortArray {
        if (list == null) return ShortArray(0)
        val array = ShortArray(list.size)
        for (i in list.indices) {
            array[i] = list[i]
        }

        return array
    }

    fun toShortList(array: ShortArray?): List<Short> {
        if (array == null) return ArrayList()
        val list = ArrayList<Short>(array.size)
        for (i in list.indices) {
            list.add(array[i])
        }

        return list
    }

    fun toArrayList(list: List<String>): ArrayList<String> {
        if (list is ArrayList<*>) return list as ArrayList<String>
        val arrayList = ArrayList<String>(list.size)
        arrayList.addAll(list)
        return arrayList
    }

    fun toArrayList(array: Array<String>): ArrayList<String> {
        val arrayList = ArrayList<String>(array.size)
        Collections.addAll(arrayList, *array)
        return arrayList
    }

    fun toParcelableArrayList(list: List<Parcelable>): ArrayList<Parcelable> {
        val arrayList = ArrayList<Parcelable>(list.size)
        arrayList.addAll(list)
        return arrayList
    }
}
