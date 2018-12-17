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

package com.stfalcon.imageviewer.loader;

import android.widget.ImageView;

/**
 * Interface definition for a callback to be invoked when image should be loaded
 */
//N.B.! This class is written in Java for convenient use of lambdas due to languages compatibility issues.
public interface ImageLoader<T> {

    /**
     * Fires every time when image object should be displayed in a provided {@link ImageView}
     *
     * @param imageView an {@link ImageView} object where the image should be loaded
     * @param image     image data from which image should be loaded
     */
    void loadImage(ImageView imageView, T image);
}
