package com.lcy.practice.multiple

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * <Desc> BrickViewHolder 是一个经过包装的 VH 类,该类实现了一个 VH 使用的通用方法,
 * 并将 Item 中所有的视图进行了缓存,在需要重用时取出,减少每次初始化操作的时间消耗,是典型的空间换时间,
 * 能大幅提升列表滑动时的流畅性.
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 **/
class BrickViewHolder(val layoutCode: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * 子视图缓存.
     */
    private val caches = SparseArray<View?>()

    /**
     * 获取指定 ID 的视图.
     */
    fun <T : View> getView(@IdRes viewId: Int): T? {
        var cache = caches[viewId]
        if (cache == null) {
            cache = itemView.findViewById(viewId)
            caches.put(viewId, cache)
        }
        return cache as T?
    }
}