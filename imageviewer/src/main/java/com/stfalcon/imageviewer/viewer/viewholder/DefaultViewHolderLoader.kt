package com.stfalcon.imageviewer.viewer.viewholder

import com.github.chrisbanes.photoview.PhotoView


class DefaultViewHolderLoader<T> : ViewHolderLoader<T> {
    override fun loadViewHolder(photoView: PhotoView) = DefaultViewHolder<T>(photoView)
}