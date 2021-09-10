package com.lcy.practice.manager

import android.util.Log
import android.widget.TextView
import com.lcy.practice.R
import com.lcy.practice.entity.User
import com.lcy.practice.multiple.BrickViewHolder
import com.lcy.practice.multiple.BaseBrickViewManager
import com.orhanobut.logger.Logger

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:39
 * 15708478830@163.com
 **/
class HomeViewManager : BaseBrickViewManager<User>() {
    override fun getLayoutResId(layoutCode: Int) = when(layoutCode % 3) {
        0 -> R.layout.manager_layout_1
        1 -> R.layout.manager_layout_2
        2 -> R.layout.manager_layout_3
        else -> R.layout.manager_layout_1
    }

    override fun getClickViewId(): MutableList<Int> = mutableListOf(
        R.id.image,
        R.id.text
    )

    override fun onBindVH(holder: BrickViewHolder, count: Int, position: Int, data: User) {
        val text: TextView? = holder.getView(R.id.text)
        text?.text = data.name
    }
}