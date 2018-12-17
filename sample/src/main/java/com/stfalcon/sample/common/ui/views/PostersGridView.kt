package com.stfalcon.sample.common.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.stfalcon.sample.R
import com.stfalcon.sample.common.models.Demo
import com.stfalcon.sample.common.models.Poster
import kotlinx.android.synthetic.main.view_posters_grid.view.*

class PostersGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var imageLoader: ((ImageView, Poster?) -> Unit)? = null
    var onPosterClick: ((Int, ImageView) -> Unit)? = null

    val imageViews by lazy {
        mapOf<Int, ImageView>(
            0 to postersFirstImage,
            1 to postersSecondImage,
            2 to postersThirdImage,
            3 to postersFourthImage,
            4 to postersFifthImage,
            5 to postersSixthImage,
            6 to postersSeventhImage,
            7 to postersEighthImage,
            8 to postersNinthImage)
    }

    init {
        View.inflate(context, R.layout.view_posters_grid, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        imageViews.values.forEachIndexed { index, imageView ->
            imageLoader?.invoke(imageView, Demo.posters.getOrNull(index))
            imageView.setOnClickListener { onPosterClick?.invoke(index, imageView) }
        }
    }
}