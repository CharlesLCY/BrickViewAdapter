package com.lcy.practice.multiple

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 * <Desc> BrickViewManager类,主要负责处理相同类型的 ItemView.
 *
 * 在使用时需要指定泛型约束,并将 Item 中所有的视图进行了缓存,在需要重用时取出,减少每次初始化操作的时间消耗,
 * 是典型的空间换时间,能大幅提升列表滑动时的流畅性.
 *
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
</Desc> */
abstract class BrickViewManager<T> {
    /**
     * 获取 Layout 资源 ID.
     *
     * @param layoutCode [BrickViewSupport] 接口获取的布局编号,由数据对象实现,可用于实现同数据的不同布局.
     */
    @LayoutRes
    abstract fun getLayoutResId(layoutCode: Int): Int

    /**
     * 绑定 Item 资源.
     *
     * @param holder   VH 对象.
     * @param count 子元素数目.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    open fun onBindViewHolder(holder: BrickViewHolder, count: Int, position: Int, data: T) {}

    /**
     * 绑定 Item 资源.
     *
     * @param holder   VH 对象.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    open fun onBindViewHolder(holder: BrickViewHolder, position: Int, data: T, payloads: List<*>?) {
        // 此处默认为空实现,子类按需实现当前方法.
    }

    /**
     * 绑定 Event 资源.
     *
     * @param holder   VH 对象.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    open fun onBindViewEvent(holder: BrickViewHolder, position: Int, data: T) {
        // 此处默认为空实现,子类按需实现当前方法.
    }

    /**
     * 创建新的 VH 对象实例,由包内的 BrickAdapter 适配器调用,用于生成相同且重复的布局.
     *
     * @param parent 父视图.
     * @return 返回新的 VH 对象.
     */
    fun createNewBrickViewHolder(parent: ViewGroup, layoutCode: Int): BrickViewHolder {
        return if (getLayoutResId(layoutCode) == 0) {
            BrickViewHolder(layoutCode, getHolderView(parent.context))
        } else {
            BrickViewHolder(layoutCode, LayoutInflater.from(parent.context)
                    .inflate(getLayoutResId(layoutCode), parent, false))
        }
    }
    ////////////////////////////////////////////////////////////
    ////////////////////  辅助方法
    ////////////////////////////////////////////////////////////
    /**
     * 设置隐藏.
     *
     * @param parent 父视图.
     * @param value  字符串值,用于判断.
     * @param ids    显示/隐藏的视图 ID.
     */
    fun setVisibility(parent: View, value: String?, @IdRes vararg ids: Int) {
        val isVisible = !TextUtils.isEmpty(value)
        for (id in ids) {
            parent.findViewById<View>(id).visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    /**
     * 设置隐藏.
     *
     * @param parent    父视图.
     * @param isVisible 显示/隐藏标识.
     * @param ids       显示/隐藏的视图 ID.
     */
    fun setVisibility(parent: View, isVisible: Boolean, @IdRes vararg ids: Int) {
        for (id in ids) {
            parent.findViewById<View>(id).visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private fun getHolderView(context: Context?): View {
        return View(context)
    }
}