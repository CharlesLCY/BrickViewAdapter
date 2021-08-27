package com.lcy.practice.multiple

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/27 18:03
 * 15708478830@163.com
 **/
class BrickViewHolder(val layoutCode: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val caches = SparseArray<View?>()

    fun <T : View> getView(@IdRes viewId: Int): T? {
        var cache = caches[viewId]
        if (cache == null) {
            cache = itemView.findViewById(viewId)
            caches.put(viewId, cache)
        }
        return cache as T?
    }
}