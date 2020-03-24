package com.stfalcon.imageviewer.loader;

import android.view.View;
import android.widget.ImageView;

import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.viewer.dialog.ImageViewerDialog;

/**
 * Interface definition for a callback to be invoked when the overlay should be loaded
 */
//N.B.! This class is written in Java for convenient use of lambdas due to languages compatibility issues.
public interface OverlayLoader<T> {

    /**
     * Fires when the overlay needs to be loaded
     *
     */
    View loadOverlayFor(int position, ImageViewerDialog<T> viewer);
}
