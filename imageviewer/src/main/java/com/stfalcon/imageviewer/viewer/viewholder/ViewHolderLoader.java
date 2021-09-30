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

package com.stfalcon.imageviewer.viewer.viewholder;

import com.github.chrisbanes.photoview.PhotoView;

/**
 * Interface definition for a callback to be invoked when a ViewHolder must be created.
 *
 * Applications can use their own subclasses of DefaultViewHolder by passing a ViewHolderLoader
 * implementation to StfalconImageViewer.Builder that returns their DefaultViewHolder subclass.
 *
 * N.B.! This class is written in Java for convenient use of lambdas due to languages compatibility issues.
 */
public interface ViewHolderLoader<T> {

    /**
     * Fires every time a new ViewHolder should be created
     *
     * @param photoView a {@link PhotoView} object, configured to behave well
     */
    DefaultViewHolder<T> loadViewHolder(PhotoView photoView);
}
