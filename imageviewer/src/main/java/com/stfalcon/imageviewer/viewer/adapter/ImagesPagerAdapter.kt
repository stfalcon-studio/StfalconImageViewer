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

package com.stfalcon.imageviewer.viewer.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.chrisbanes.photoview.PhotoView
import com.stfalcon.imageviewer.common.pager.RecyclingPagerAdapter
import com.stfalcon.imageviewer.loader.ImageLoader
import com.stfalcon.imageviewer.viewer.viewholder.ViewHolderLoader
import com.stfalcon.imageviewer.viewer.viewholder.DefaultViewHolder

internal class ImagesPagerAdapter<T>(
    private val context: Context,
    _images: List<T>,
    private val imageLoader: ImageLoader<T>,
    private val isZoomingAllowed: Boolean,
    private val viewHolderLoader: ViewHolderLoader<T>
) : RecyclingPagerAdapter<DefaultViewHolder<T>>() {
    private var images = _images
    private val holders = mutableListOf<DefaultViewHolder<T>>()
    private var primaryPos = -1

    companion object {
        val photoViewId = View.generateViewId()
    }

    fun isScaled(position: Int): Boolean =
        holders.firstOrNull { it.position == position }?.isScaled() ?: false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder<T> {
        val photoView = PhotoView(context).apply {
            id = photoViewId
            isEnabled = isZoomingAllowed
            setOnViewDragListener { _, _ -> setAllowParentInterceptOnEdge(scale == 1.0f) }
        }

        return viewHolderLoader.loadViewHolder(photoView).also {
            it.imageLoader = imageLoader
            holders.add(it) }
    }

    override fun onBindViewHolder(holder: DefaultViewHolder<T>, position: Int) = holder.bind(position, images[position])

    override fun getItemCount() = images.size

    internal fun updateImages(images: List<T>) {
        this.images = images
        notifyDataSetChanged()
    }

    internal fun resetScale(position: Int) =
        holders.firstOrNull { it.position == position }?.resetScale()

    fun onDialogClosed() = holders.forEach { it.onDialogClosed() }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)

        // Only fire when the primary item has actually changed
        if (position != primaryPos) {
            primaryPos = position
            holders.forEach { it.setIsVisible(it.position == primaryPos) }
        }
    }
}