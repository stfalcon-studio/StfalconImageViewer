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

import androidx.transition.Transition

internal fun Transition.addListener(
    onTransitionEnd: ((Transition) -> Unit)? = null,
    onTransitionResume: ((Transition) -> Unit)? = null,
    onTransitionPause: ((Transition) -> Unit)? = null,
    onTransitionCancel: ((Transition) -> Unit)? = null,
    onTransitionStart: ((Transition) -> Unit)? = null
) = addListener(
    object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            onTransitionEnd?.invoke(transition)
        }

        override fun onTransitionResume(transition: Transition) {
            onTransitionResume?.invoke(transition)
        }

        override fun onTransitionPause(transition: Transition) {
            onTransitionPause?.invoke(transition)
        }

        override fun onTransitionCancel(transition: Transition) {
            onTransitionCancel?.invoke(transition)
        }

        override fun onTransitionStart(transition: Transition) {
            onTransitionStart?.invoke(transition)
        }
    })