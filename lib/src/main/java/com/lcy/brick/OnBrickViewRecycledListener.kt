package com.lcy.brick

/**
 * Brick View 回收监听.
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 **/
interface OnBrickViewRecycledListener {
    /**
     * 当 View 回收回调事件.
     *
     * @param brickViewHolder VH.
     */
    fun onViewRecycled(brickViewHolder: BrickViewHolder)
}