package com.lcy.practice.multiple;

/**
 * <Desc> Brick View 回收监听.
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 */
public interface OnBrickViewRecycledListener {

    /**
     * 当 View 回收回调事件.
     *
     * @param multipleViewHolder VH.
     */
    void onViewRecycled(BrickViewHolder multipleViewHolder);
}
