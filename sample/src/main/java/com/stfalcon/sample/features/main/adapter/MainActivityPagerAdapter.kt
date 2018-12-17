package com.stfalcon.sample.features.main.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.stfalcon.sample.R
import com.stfalcon.sample.features.main.card.DemoCardFragment

class MainActivityPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    companion object {
        const val ID_IMAGES_GRID = 0
        const val ID_SCROLL = 1
        const val ID_STYLING = 2
        const val ID_ROTATION = 3
    }

    override fun getItem(position: Int): Fragment {
        var title = ""
        var description = ""
        when (position) {
            ID_IMAGES_GRID -> {
                title = context.getString(R.string.action_images_grid)
                description = context.getString(R.string.description_images_grid)
            }
            ID_SCROLL -> {
                title = context.getString(R.string.action_scroll)
                description = context.getString(R.string.description_scroll)
            }
            ID_STYLING -> {
                title = context.getString(R.string.action_style)
                description = context.getString(R.string.action_description_styled_view)
            }
            ID_ROTATION -> {
                title = context.getString(R.string.action_rotation)
                description = context.getString(R.string.description_rotation_support)
            }
        }
        return DemoCardFragment.newInstance(position, title, description)
    }

    override fun getCount() = 4
}