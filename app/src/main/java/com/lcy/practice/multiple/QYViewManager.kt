package com.lcy.practice.multiple

import android.view.View

/**
 * Desc:
 * @author Charlie叶晨
 * @date   2020/6/17 12:08
 * @email  15708478830@163.com
 **/
abstract class QYViewManager<T> : MultipleViewManager<T>() {
    private var onItemClick: ((holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit)? = null
    private var onItemLongClick: ((holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit)? = null

    /**
     * 设置item点击事件
     */
    open fun setOnItemClickListener(onItemClick: (holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    /**
     * 点击事件触发
     */
    open fun onItemClick(holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) {
        onItemClick?.invoke(holder, v, data, position)
    }

    /**
     * 设置item长按事件
     */
    open fun setOnItemLongClickListener(onItemLongClick: (holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit) {
        this.onItemLongClick = onItemLongClick
    }

    /**
     * 长按事件触发
     */
    open fun onItemLongClick(holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) {
        onItemLongClick?.invoke(holder, v, data, position)
    }
}