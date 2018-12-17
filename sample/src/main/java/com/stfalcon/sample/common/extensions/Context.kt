package com.stfalcon.sample.common.extensions

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

fun Context.showShortToast(textRes: Int) {
    showShortToast(getString(textRes))
}

fun Context.showShortToast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}


fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableRes)
}

fun Context.sendShareIntent(text: String) {
    startActivity(Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    })
}