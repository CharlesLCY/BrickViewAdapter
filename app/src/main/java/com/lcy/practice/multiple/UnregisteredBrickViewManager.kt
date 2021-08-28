package com.lcy.practice.multiple

import android.widget.TextView
import com.lcy.practice.R

/**
 * 未注册 Item 管理器.
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 */
class UnregisteredBrickViewManager : BrickViewManager<UnregisteredObject>() {
    /**
     * 获取 Layout 资源 ID.
     *
     * @param layoutCode [BrickViewSupport] 接口获取的布局编号,由数据对象实现,可用于实现同数据的不同布局.
     */
    override fun getLayoutResId(layoutCode: Int) = R.layout.unregistered_multiple_layout

    /**
     * 绑定 Item 资源.
     *ø
     * @param holder   VH 对象.
     * @param position 点击的视图对应的 item 位置.
     * @param data     点击的视图对应的 item 数据.
     */
    override fun onBindViewHolder(
        holder: BrickViewHolder,
        count: Int,
        position: Int,
        data: UnregisteredObject
    ) {
        val title = holder.getView<TextView>(R.id.text_view_title)
        val message = holder.getView<TextView>(R.id.text_view_message)
        title?.text = data.title
        message?.text = data.message
    }
}