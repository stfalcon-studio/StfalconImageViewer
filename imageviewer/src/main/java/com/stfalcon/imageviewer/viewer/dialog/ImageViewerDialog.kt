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

package com.stfalcon.imageviewer.viewer.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.stfalcon.imageviewer.R
import com.stfalcon.imageviewer.StfalconImageViewer
import com.stfalcon.imageviewer.listeners.OnDismissListener
import com.stfalcon.imageviewer.listeners.OnImageChangeListener
import com.stfalcon.imageviewer.loader.ImageLoader
import com.stfalcon.imageviewer.loader.OverlayLoader
import com.stfalcon.imageviewer.viewer.builder.BuilderData
import com.stfalcon.imageviewer.viewer.view.ImageViewerView
import kotlin.math.max

class ImageViewerDialog<T>: DialogFragment() {

    private lateinit var dialog: AlertDialog
    private lateinit var viewerView: ImageViewerView<T>
    private var animateOpen = true
    private lateinit var builderData: BuilderData<T>

    private val dialogStyle: Int
        get() = if (builderData.shouldStatusBarHide)
            R.style.ImageViewerDialog_NoStatusBar
        else
            R.style.ImageViewerDialog_Default

    companion object {
        private const val builderDataKey = "BuilderDataKey"
        fun <T> newInstance(builderData: BuilderData<T>): ImageViewerDialog<T> {
            val args = Bundle()
            args.putSerializable(builderDataKey, builderData)
            val f = ImageViewerDialog<T>()
            f.arguments = args
            return f
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        builderData = arguments?.getSerializable(builderDataKey) as BuilderData<T>
        setupViewerView()
        dialog = AlertDialog
            .Builder(requireContext(), dialogStyle)
            .setView(viewerView)
            .setOnKeyListener { _, keyCode, event -> onDialogKeyEvent(keyCode, event) }
            .create()
            .apply {
                setOnShowListener { viewerView.open(builderData.transitionView, animateOpen) }
                setOnDismissListener { ((targetFragment ?: activity) as? OnDismissListener)?.onDismiss() }
}
        return dialog
    }

    fun show(fragmentManager: FragmentManager, animate: Boolean) {
        animateOpen = animate
        show(fragmentManager, "")
    }

    fun close() {
        viewerView.close()
    }

    override fun dismiss() {
        dialog.dismiss()
    }

    fun updateImages(images: List<T>) {
        viewerView.updateImages(images)
    }

    fun getCurrentPosition(): Int =
        viewerView.currentPosition

    fun setCurrentPosition(position: Int): Int {
        viewerView.currentPosition = position
        return viewerView.currentPosition
    }

    fun updateTransitionImage(imageView: ImageView?) {
        viewerView.updateTransitionImage(imageView)
    }

    private fun onDialogKeyEvent(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
            event.action == KeyEvent.ACTION_UP &&
            !event.isCanceled
        ) {
            if (viewerView.isScaled) {
                viewerView.resetScale()
            } else {
                viewerView.close()
            }
            return true
        }
        return false
    }

    private fun setupViewerView() {
        viewerView = ImageViewerView(requireContext())
        viewerView.apply {
            isZoomingAllowed = builderData.isZoomingAllowed
            isSwipeToDismissAllowed = builderData.isSwipeToDismissAllowed

            containerPadding = builderData.containerPaddingPixels
            imagesMargin = builderData.imageMarginPixels
            overlayView = ((targetFragment ?: activity) as? OverlayLoader<T>)?.loadOverlayFor(max(viewerView.currentPosition, builderData.startPosition),
                this@ImageViewerDialog)

            setBackgroundColor(builderData.backgroundColor)
            val imageLoader = (targetFragment ?: activity) as? ImageLoader<T>
            if (imageLoader != null) {
                setImages(builderData.images, builderData.startPosition, imageLoader)
            }

            onPageChange = { position -> ((targetFragment ?: activity) as? OnImageChangeListener)?.onImageChange(position) }
            onDismiss = {
                dialog.dismiss()
                ((targetFragment ?: activity) as? OnDismissListener)?.onDismiss()
            }
        }
    }
}
