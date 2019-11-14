package com.stfalcon.imageviewer.common.pager

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.stfalcon.imageviewer.common.extensions.forEach

internal abstract class RecyclingPagerAdapter<VH : RecyclingPagerAdapter.ViewHolder>
    : PagerAdapter() {

    companion object {
        private val STATE = RecyclingPagerAdapter::class.java.simpleName
        private const val VIEW_TYPE_IMAGE = 0
    }

    internal abstract fun getItemCount(): Int
    internal abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    internal abstract fun onBindViewHolder(holder: VH, position: Int)

    private val typeCaches = SparseArray<RecycleCache>()
    private var savedStates = SparseArray<Parcelable>()

    override fun destroyItem(parent: ViewGroup, position: Int, item: Any) {
        if (item is ViewHolder) {
            item.detach(parent)
        }
    }

    override fun getCount() = getItemCount()

    override fun getItemPosition(item: Any) = POSITION_NONE

    @Suppress("UNCHECKED_CAST")
    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        var cache = typeCaches.get(VIEW_TYPE_IMAGE)

        if (cache == null) {
            cache = RecycleCache(this)
            typeCaches.put(VIEW_TYPE_IMAGE, cache)
        }

        return cache.getFreeViewHolder(parent, VIEW_TYPE_IMAGE)
                .apply {
                    attach(parent, position)
                    onBindViewHolder(this as VH, position)
                    onRestoreInstanceState(savedStates.get(getItemId(position)))
                }
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean =
            obj is ViewHolder && obj.itemView === view

    override fun saveState(): Parcelable? {
        for (viewHolder in getAttachedViewHolders()) {
            savedStates.put(getItemId(viewHolder.position), viewHolder.onSaveInstanceState())
        }
        return Bundle().apply { putSparseParcelableArray(STATE, savedStates) }
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        if (state != null && state is Bundle) {
            state.classLoader = loader
            val sparseArray: SparseArray<Parcelable>? = state.getSparseParcelableArray(STATE)
            savedStates = sparseArray ?: SparseArray()
        }
        super.restoreState(state, loader)
    }

    private fun getItemId(position: Int) = position

    private fun getAttachedViewHolders(): List<ViewHolder> {
        val attachedViewHolders = ArrayList<ViewHolder>()

        typeCaches.forEach { _, value ->
            value.caches.forEach { holder ->
                if (holder.isAttached) {
                    attachedViewHolders.add(holder)
                }
            }
        }

        return attachedViewHolders
    }

    private class RecycleCache internal constructor(
            private val adapter: RecyclingPagerAdapter<*>
    ) {

        internal val caches = mutableListOf<ViewHolder>()

        internal fun getFreeViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var iterationsCount = 0
            var viewHolder: ViewHolder

            while (iterationsCount < caches.size) {
                viewHolder = caches[iterationsCount]
                if (!viewHolder.isAttached) {
                    return viewHolder
                }
                iterationsCount++
            }

            return adapter.onCreateViewHolder(parent, viewType).also { caches.add(it) }
        }
    }

    internal abstract class ViewHolder(internal val itemView: View) {

        companion object {
            private val STATE = ViewHolder::class.java.simpleName
        }

        internal var position: Int = 0
        internal var isAttached: Boolean = false

        internal fun attach(parent: ViewGroup, position: Int) {
            this.isAttached = true
            this.position = position
            parent.addView(itemView)
        }

        internal fun detach(parent: ViewGroup) {
            parent.removeView(itemView)
            isAttached = false
        }

        internal fun onRestoreInstanceState(state: Parcelable?) {
            getStateFromParcelable(state)?.let { itemView.restoreHierarchyState(it) }
        }

        internal fun onSaveInstanceState(): Parcelable {
            val state = SparseArray<Parcelable>()
            itemView.saveHierarchyState(state)
            return Bundle().apply { putSparseParcelableArray(STATE, state) }
        }

        private fun getStateFromParcelable(state: Parcelable?): SparseArray<Parcelable>? {
            if (state != null && state is Bundle && state.containsKey(STATE)) {
                return state.getSparseParcelableArray<Parcelable>(STATE)
            }
            return null
        }
    }
}