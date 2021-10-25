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

package com.stfalcon.imageviewer;

import android.content.Context;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.*;
import androidx.core.content.ContextCompat;
import com.stfalcon.imageviewer.listeners.OnDismissListener;
import com.stfalcon.imageviewer.listeners.OnImageChangeListener;
import com.stfalcon.imageviewer.loader.ImageLoader;
import com.stfalcon.imageviewer.viewer.builder.BuilderData;
import com.stfalcon.imageviewer.viewer.dialog.ImageViewerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//N.B.! This class is written in Java for convenient use of lambdas due to languages compatibility issues.
@SuppressWarnings({"unused", "WeakerAccess"})
public class StfalconImageViewer<T> {

    private Context context;
    private BuilderData<T> builderData;
    private ImageViewerDialog<T> dialog;

    protected StfalconImageViewer(@NonNull Context context, @NonNull BuilderData<T> builderData) {
        this.context = context;
        this.builderData = builderData;
        this.dialog = new ImageViewerDialog<>(context, builderData);
    }

    /**
     * Displays the built viewer if passed list of images is not empty
     */
    public void show() {
        show(true);
    }

    /**
     * Displays the built viewer if passed list of images is not empty
     *
     * @param animate whether the passed transition view should be animated on open. Useful for screen rotation handling.
     */
    public void show(boolean animate) {
        if (!builderData.getImages().isEmpty()) {
            dialog.show(animate);
        } else {
            Log.w(context.getString(R.string.library_name),
                    "Images list cannot be empty! Viewer ignored.");
        }
    }

    /**
     * Closes the viewer with suitable close animation
     */
    public void close() {
        dialog.close();
    }

    /**
     * Dismisses the dialog with no animation
     */
    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * Updates an existing images list if a new list is not empty, otherwise closes the viewer
     */
    public void updateImages(T[] images) {
        updateImages(new ArrayList<>(Arrays.asList(images)));
    }

    /**
     * Updates an existing images list if a new list is not empty, otherwise closes the viewer
     */
    public void updateImages(List<T> images) {
        if (!images.isEmpty()) {
            dialog.updateImages(images);
        } else {
            dialog.close();
        }
    }

    public int currentPosition() {
        return dialog.getCurrentPosition();
    }

    public int setCurrentPosition(int position){
        return dialog.setCurrentPosition(position);
    }

    /**
     * Updates transition image view.
     * Useful for a case when image position has changed and you want to update the transition animation target.
     */
    public void updateTransitionImage(ImageView imageView) {
        dialog.updateTransitionImage(imageView);
    }

    /**
     * Builder class for {@link StfalconImageViewer}
     */
    public static class Builder<T> {

        private Context context;
        private BuilderData<T> data;

        public Builder(Context context, T[] images, ImageLoader<T> imageLoader) {
            this(context, new ArrayList<>(Arrays.asList(images)), imageLoader);
        }

        public Builder(Context context, List<T> images, ImageLoader<T> imageLoader) {
            this.context = context;
            this.data = new BuilderData<>(images, imageLoader);
        }

        /**
         * Sets a position to start viewer from.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withStartPosition(int position) {
            this.data.setStartPosition(position);
            return this;
        }

        /**
         * Sets a background color value for the viewer
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withBackgroundColor(@ColorInt int color) {
            this.data.setBackgroundColor(color);
            return this;
        }

        /**
         * Sets a background color resource for the viewer
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withBackgroundColorResource(@ColorRes int color) {
            return this.withBackgroundColor(ContextCompat.getColor(context, color));
        }

        /**
         * Sets custom overlay view to be shown over the viewer.
         * Commonly used for image description or counter displaying.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withOverlayView(View view) {
            this.data.setOverlayView(view);
            return this;
        }

        /**
         * Sets space between the images using dimension.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withImagesMargin(@DimenRes int dimen) {
            this.data.setImageMarginPixels(Math.round(context.getResources().getDimension(dimen)));
            return this;
        }

        /**
         * Sets space between the images in pixels.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withImageMarginPixels(int marginPixels) {
            this.data.setImageMarginPixels(marginPixels);
            return this;
        }

        /**
         * Sets overall padding for zooming and scrolling area using dimension.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withContainerPadding(@DimenRes int padding) {
            int paddingPx = Math.round(context.getResources().getDimension(padding));
            return withContainerPaddingPixels(paddingPx, paddingPx, paddingPx, paddingPx);
        }

        /**
         * Sets `start`, `top`, `end` and `bottom` padding for zooming and scrolling area using dimension.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withContainerPadding(@DimenRes int start, @DimenRes int top,
                                               @DimenRes int end, @DimenRes int bottom
        ) {
            withContainerPaddingPixels(
                    Math.round(context.getResources().getDimension(start)),
                    Math.round(context.getResources().getDimension(top)),
                    Math.round(context.getResources().getDimension(end)),
                    Math.round(context.getResources().getDimension(bottom)));
            return this;
        }

        /**
         * Sets overall padding for zooming and scrolling area in pixels.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withContainerPaddingPixels(@Px int padding) {
            this.data.setContainerPaddingPixels(new int[]{padding, padding, padding, padding});
            return this;
        }

        /**
         * Sets `start`, `top`, `end` and `bottom` padding for zooming and scrolling area in pixels.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withContainerPaddingPixels(int start, int top, int end, int bottom) {
            this.data.setContainerPaddingPixels(new int[]{start, top, end, bottom});
            return this;
        }

        /**
         * Sets status bar visibility. True by default.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withHiddenStatusBar(boolean value) {
            this.data.setShouldStatusBarHide(value);
            return this;
        }

        /**
         * Sets alert dialog style. ImageViewerDialog.Default by default.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withDialogStyle(int value) {
            this.data.setDialogStyle(value);
            return this;
        }

        /**
         * Sets status bar transparency to allow drawing underneath it. False by default.
         * Works only on API 21 and above.
         *
         * @return This Builder object to allow calls chaining
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        public Builder<T> shouldStatusBarTransparent(boolean value) {
            this.data.setShouldStatusBarTransparent(value);
            return this;
        }

        /**
         * Enables or disables zooming. True by default.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> allowZooming(boolean value) {
            this.data.setZoomingAllowed(value);
            return this;
        }

        /**
         * Enables or disables the "Swipe to Dismiss" gesture. True by default.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> allowSwipeToDismiss(boolean value) {
            this.data.setSwipeToDismissAllowed(value);
            return this;
        }

        /**
         * Sets a target {@link ImageView} to be part of transition when opening or closing the viewer/
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withTransitionFrom(ImageView imageView) {
            this.data.setTransitionView(imageView);
            return this;
        }

        /**
         * Sets {@link OnImageChangeListener} for the viewer.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withImageChangeListener(OnImageChangeListener imageChangeListener) {
            this.data.setImageChangeListener(imageChangeListener);
            return this;
        }

        /**
         * Sets {@link OnDismissListener} for viewer.
         *
         * @return This Builder object to allow calls chaining
         */
        public Builder<T> withDismissListener(OnDismissListener onDismissListener) {
            this.data.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * Creates a {@link StfalconImageViewer} with the arguments supplied to this builder. It does not
         * show the dialog. This allows the user to do any extra processing
         * before displaying the dialog. Use {@link #show()} if you don't have any other processing
         * to do and want this to be created and displayed.
         */
        public StfalconImageViewer<T> build() {
            return new StfalconImageViewer<>(context, data);
        }

        /**
         * Creates the {@link StfalconImageViewer} with the arguments supplied to this builder and
         * shows the dialog.
         */
        public StfalconImageViewer<T> show() {
            return show(true);
        }

        /**
         * Creates the {@link StfalconImageViewer} with the arguments supplied to this builder and
         * shows the dialog.
         *
         * @param animate whether the passed transition view should be animated on open. Useful for screen rotation handling.
         */
        public StfalconImageViewer<T> show(boolean animate) {
            StfalconImageViewer<T> viewer = build();
            viewer.show(animate);
            return viewer;
        }
    }
}
