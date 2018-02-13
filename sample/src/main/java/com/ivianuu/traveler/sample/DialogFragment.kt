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

package com.ivianuu.traveler.sample

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.ivianuu.traveler.keys.DialogFragmentKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class DialogKey(val count: Int): DialogFragmentKey(), Parcelable {
    override fun createDialogFragment(): DialogFragment = com.ivianuu.traveler.sample.DialogFragment()
    @IgnoredOnParcel override val fragmentTag: String = "dialog_fragment_$count"
}

/**
 * @author Manuel Wrage (IVIanuu)
 */
class DialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val key = DialogFragmentKey.get<DialogKey>(this)
        return AlertDialog.Builder(context!!)
            .setTitle("Dialog")
            .setMessage("current count is $key.count")
            .create()
    }

}