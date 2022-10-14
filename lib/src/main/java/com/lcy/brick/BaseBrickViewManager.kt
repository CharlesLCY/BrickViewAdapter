package com.lcy.brick

import android.view.View

/**
 * 搭建积木的基础父类，开发者通常应继承该父类创建的列表ViewManager
 * 扩展BrickViewManager，增加View点击事件
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 **/
abstract class BaseBrickViewManager<T> : BrickViewManager<T>() {
    private var onItemClick: ((holder: BrickViewHolder?, v: View?, data: Any?, position: Int) -> Unit)? = null
    private var onItemLongClick: ((holder: BrickViewHolder?, v: View?, data: Any?, position: Int) -> Unit)? = null

    abstract fun onBindVH(holder: BrickViewHolder, count: Int, position: Int, data: T)

    override fun onBindViewHolder(holder: BrickViewHolder, count: Int, position: Int, data: T) {
        super.onBindViewHolder(holder, count, position, data)
        setupClickView(getClickViewId(), holder, position, data)
        setupLongClickView(getLongClickViewId(), holder, position, data)
        onBindVH(holder, count, position, data)
    }

    override fun onBindViewHolder(
        holder: BrickViewHolder,
        position: Int,
        data: T,
        payloads: List<*>?
    ) {
        super.onBindViewHolder(holder, position, data, payloads)
        setupClickView(getClickViewId(), holder, position, data)
        setupLongClickView(getLongClickViewId(), holder, position, data)
    }

    override fun onBindViewEvent(holder: BrickViewHolder, position: Int, data: T) {
        super.onBindViewEvent(holder, position, data)
        setupClickView(getClickViewId(), holder, position, data)
        setupLongClickView(getLongClickViewId(), holder, position, data)
    }

    /**
     * 设置item点击事件
     */
    fun setOnItemClickListener(onItemClick: (holder: BrickViewHolder?, v: View?, data: Any?, position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    /**
     * 点击事件触发
     */
    private fun onItemClick(holder: BrickViewHolder?, v: View?, data: Any?, position: Int) {
        onItemClick?.invoke(holder, v, data, position)
    }

    /**
     * 设置item长按事件
     */
    fun setOnItemLongClickListener(onItemLongClick: (holder: BrickViewHolder?, v: View?, data: Any?, position: Int) -> Unit) {
        this.onItemLongClick = onItemLongClick
    }

    /**
     * 长按事件触发
     */
    private fun onItemLongClick(holder: BrickViewHolder?, v: View?, data: Any?, position: Int) {
        onItemLongClick?.invoke(holder, v, data, position)
    }

    /**
     * 设置需要点击事件的View id集合
     */
    open fun getClickViewId(): MutableList<Int> = mutableListOf()

    /**
     * 设置需要长按事件的View id集合
     */
    open fun getLongClickViewId(): MutableList<Int> = mutableListOf()

    private fun setupClickView(ids: MutableList<Int>, holder: BrickViewHolder, position: Int, data: T) {
        if (ids.isNullOrEmpty()) return

        ids.forEach {
            val view: View? = holder.getView(it)
            view?.setOnClickListener {
                onItemClick(holder, view, data, position)
            }
        }
    }

    private fun setupLongClickView(ids: MutableList<Int>, holder: BrickViewHolder, position: Int, data: T) {
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