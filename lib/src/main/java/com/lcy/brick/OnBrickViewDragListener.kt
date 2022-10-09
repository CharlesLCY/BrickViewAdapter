package com.lcy.brick

import android.view.View

/**
 * 列表 Item 拖拽/滑动回调
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 **/
interface OnBrickViewDragListener {
    fun onItemIdle(itemView: View?)

    fun onItemSwipe(itemView: View?)

    fun onItemDrag(itemView: View?)

    fun canDropOver(): Boolean

    val isItemViewSwipeEnabled: Boolean

    val isLongPressDragEnabled: Boolean

    /**
     * 用于判断拖动后是否完成数据交换
     *
     * @param allData      所有的数据
     * @param formPosition 预交换位置
     * @param toPosition   交换目标位置
     * @return 返回true 则会交换位置,否则不予交换,任何情况都交换就直接返回true
     */
    fun canMove(allData: MutableList<Any>, formPosition: Int, toPosition: Int): Boolean

    /**
     * 用于判断侧滑的条目 是否删除
     *
     * @param allData        所有数据
     * @param removePosition 将要被删除的Item
     * @return 返回true 将会删除,否则不予删除
     */
    fun canRemove(allData: MutableList<Any>, removePosition: Int): Boolean
}