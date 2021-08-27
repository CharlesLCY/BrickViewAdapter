package com.lcy.practice.multiple;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Desc: Multiple View Holder 是一个经过包装的 VH 类,该类实现了一个 VH 使用的通用方法,
 * 并将 Item 中所有的视图进行了缓存,在需要重用时取出,减少每次初始化操作的时间消耗,是典型的空间换时间,
 * 能大幅提升列表滑动时的流畅性.
 * @Author 吴荣
 * @Date 2018-07-10
 * @Time 11:49
 */
@SuppressWarnings("JavaDoc")
public final class BrickViewHolder extends RecyclerView.ViewHolder {

    /**
     * 用于记录布局编号,避免复用问题.
     */
    private int layoutCode;

    /**
     * 子视图缓存.
     */
    private SparseArray<View> caches;

    /**
     * 构造器
     */
    BrickViewHolder(int layoutCode, View itemView) {
        super(itemView);
        this.layoutCode = layoutCode;
        this.caches = new SparseArray<>();
    }

    /**
     * 获取指定 ID 的视图.
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View cache = caches.get(viewId);
        if (cache == null) {
            cache = itemView.findViewById(viewId);
            caches.put(viewId, cache);
        }
        // noinspection unchecked
        return (T) cache;
    }

    public int getLayoutCode() {
        return layoutCode;
    }
}
