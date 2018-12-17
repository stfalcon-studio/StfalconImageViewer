/*
 * Copyright 2018 stfalcon.com
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

package com.stfalcon.imageviewer.common.extensions

import android.util.SparseArray
import java.util.ConcurrentModificationException

internal inline fun <T> SparseArray<T>.forEach(block: (Int, T) -> Unit) {
    val size = this.size()
    for (index in 0 until size) {
        if (size != this.size()) throw ConcurrentModificationException()
        block(this.keyAt(index), this.valueAt(index))
    }
}