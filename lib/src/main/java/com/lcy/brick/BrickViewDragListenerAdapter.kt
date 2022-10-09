package com.lcy.brick

import android.view.View

/**
 * 列表拖动监听器基础实现类
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 **/
open class BrickViewDragListenerAdapter(
    override val isItemViewSwipeEnabled: Boolean = false,
    override val isLongPressDragEnabled: Boolean = false
) : OnBrickViewDragListener {
    override fun onItemIdle(itemView: View?) {
    }

    override fun onItemSwipe(itemView: View?) {
    }

    override fun onItemDrag(itemView: View?) {
    }

    override fun canDropOver(): Boolean = false

    override fun canMove(allData: MutableList<Any>, formPosition: Int, toPosition: Int): Boolean = false

    override fun canRemove(allData: MutableList<Any>, removePosition: Int): Boolean = false
}