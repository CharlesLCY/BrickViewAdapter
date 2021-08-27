package com.lcy.practice.multiple;

/**
 * Multiple View 回收监听.
 * <p>
 * Create By 吴荣 at 2019-01-10 15:50.
 */
public interface OnBrickViewRecycledListener {

    /**
     * 当 View 回收回调事件.
     *
     * @param multipleViewHolder VH.
     */
    void onViewRecycled(BrickViewHolder multipleViewHolder);
}
