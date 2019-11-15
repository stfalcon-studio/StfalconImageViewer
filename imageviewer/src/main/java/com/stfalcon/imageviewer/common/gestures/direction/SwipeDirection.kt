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

package com.stfalcon.imageviewer.common.gestures.direction

internal enum class SwipeDirection {
    NOT_DETECTED,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        fun fromAngle(angle: Double): SwipeDirection {
            return when (angle) {
                in 0.0..45.0 -> RIGHT
                in 45.0..135.0 -> UP
                in 135.0..225.0 -> LEFT
                in 225.0..315.0 -> DOWN
                in 315.0..360.0 -> RIGHT
                else -> NOT_DETECTED
            }
        }
    }
}