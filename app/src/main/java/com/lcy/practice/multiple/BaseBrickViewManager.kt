package com.lcy.practice.multiple

import android.view.View

/**
 * Desc:
 * @author Charlie叶晨
 * @date   2020/6/17 12:08
 * @email  15708478830@163.com
 **/
abstract class BaseBrickViewManager<T> : BrickViewManager<T>() {
    private var onItemClick: ((holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit)? = null
    private var onItemLongClick: ((holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit)? = null

    abstract fun onBindVH(holder: MultipleViewHolder, count: Int, position: Int, data: T)

    override fun onBindViewHolder(holder: MultipleViewHolder, count: Int, position: Int, data: T) {
        super.onBindViewHolder(holder, count, position, data)
        setupClickView(getClickViewId(), holder, position, data)
        setupLongClickView(getLongClickViewId(), holder, position, data)
        onBindVH(holder, count, position, data)
    }

    override fun onBindViewHolder(
        holder: MultipleViewHolder,
        position: Int,
        data: T,
        payloads: MutableList<Any?>?
    ) {
        super.onBindViewHolder(holder, position, data, payloads)
        setupClickView(getClickViewId(), holder, position, data)
        setupLongClickView(getLongClickViewId(), holder, position, data)
    }

    override fun onBindViewEvent(holder: MultipleViewHolder, position: Int, data: T) {
        super.onBindViewEvent(holder, position, data)
        setupClickView(getClickViewId(), holder, position, data)
        setupLongClickView(getLongClickViewId(), holder, position, data)
    }

    /**
     * 设置item点击事件
     */
    fun setOnItemClickListener(onItemClick: (holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    /**
     * 点击事件触发
     */
    private fun onItemClick(holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) {
        onItemClick?.invoke(holder, v, data, position)
    }

    /**
     * 设置item长按事件
     */
    fun setOnItemLongClickListener(onItemLongClick: (holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) -> Unit) {
        this.onItemLongClick = onItemLongClick
    }

    /**
     * 长按事件触发
     */
    private fun onItemLongClick(holder: MultipleViewHolder?, v: View?, data: Any?, position: Int) {
        onItemLongClick?.invoke(holder, v, data, position)
    }

    /**
     * 获取点击的View id集合
     */
    open fun getClickViewId(): MutableList<Int> = mutableListOf()

    /**
     * 获取长按的View id集合
     */
    open fun getLongClickViewId(): MutableList<Int> = mutableListOf()

    private fun setupClickView(ids: MutableList<Int>, holder: MultipleViewHolder, position: Int, data: T) {
        if (ids.isNullOrEmpty()) return

        ids.forEach {
            val view: View? = holder.getView(it)
            view?.setOnClickListener {
                onItemClick(holder, view, data, position)
            }
        }
    }

    private fun setupLongClickView(ids: MutableList<Int>, holder: MultipleViewHolder, position: Int, data: T) {
        if (ids.isNullOrEmpty()) return

        ids.forEach {
            val view: View? = holder.getView(it)
            view?.setOnLongClickListener {
                onItemLongClick(holder, view, data, position)
                true
            }
        }
    }
}