package com.stfalcon.imageviewer.viewer.viewholder

import android.view.View
import com.github.chrisbanes.photoview.PhotoView
import com.stfalcon.imageviewer.common.extensions.resetScale
import com.stfalcon.imageviewer.common.pager.RecyclingPagerAdapter
import com.stfalcon.imageviewer.loader.ImageLoader
import com.stfalcon.imageviewer.viewer.adapter.ImagesPagerAdapter


/**
 * Subclasses may customize how the image pages are presented to the user.
 * By overriding the constructor, subclasses may add additional views. The constructor is passed
 * a zoomable PhotoView which is configured to behave well. Subclasses may customize this View,
 * incorporate it into a more complex hierarchy, or ignore it altogether. The final View or
 * ViewGroup must be passed to the super() constructor.
 * By overriding the bind() method, subclasses may customize how images are loaded into views.
 */
open class DefaultViewHolder<T>(itemView: View)
    : RecyclingPagerAdapter.ViewHolder(itemView) {

    internal var imageLoader: ImageLoader<T>? = null

    // If a subclass has incorporated the PhotoView into a ViewGroup, find it
    // to ensure correct behavior by default
    private var photoView: PhotoView? =
            if (itemView is PhotoView) itemView
            else itemView.findViewById(ImagesPagerAdapter.photoViewId)

    // Subclasses should return True when they wish to handle Back button presses
    // (e.g. when the image is zoomed in and should be un-zoomed when Back is pressed)
    open fun isScaled() : Boolean = (photoView?.scale ?: 1f) > 1f

    // Subclasses can respond to Back button presses here when isScaled() returns True
    open fun resetScale() = photoView?.resetScale(animate = true)

    open fun bind(position: Int, image: T) {
        this.position = position
        imageLoader?.loadImage(photoView, image)
    }

    // Subclasses may respond when the dialog window is closed (e.g. to stop video playback)
    open fun onDialogClosed() {}

    // Subclasses may respond when this ViewHolder's View moves on or off the screen
    open fun setIsVisible(isVisible: Boolean) {}
}