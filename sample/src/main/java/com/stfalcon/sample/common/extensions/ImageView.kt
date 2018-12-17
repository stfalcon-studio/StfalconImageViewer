package com.stfalcon.sample.common.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?) =
    Picasso.get().load(url).into(this)
//    GlideApp.with(context)
//        .load(url ?: "")
//        .into(this)