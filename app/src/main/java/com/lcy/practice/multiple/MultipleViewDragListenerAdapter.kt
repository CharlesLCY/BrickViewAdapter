package com.lcy.practice.multiple

import android.view.View

/**
 * Desc: 列表拖动监听器基础实现类
 * @author Charlie叶晨
 * @date   2020-01-07 10:41
 * @email  15708478830@163.com
 **/
open class MultipleViewDragListenerAdapter : OnMultipleViewDragChangeListener {
    override fun onItemIdle(itemView: View?) {
    }

    override fun onItemSwipe(itemView: View?) {
    }

    override fun onItemDrag(itemView: View?) {
    }

    override fun canDropOver(): Boolean = false

    override fun isItemViewSwipeEnabled(): Boolean = false

    override fun isLongPressDragEnabled(): Boolean = false

    override fun canMove(allData: MutableList<Any>?, formPosition: Int, toPosition: Int): Boolean = false

    override fun canRemove(allData: MutableList<Any>?, removePosition: Int): Boolean = false
}