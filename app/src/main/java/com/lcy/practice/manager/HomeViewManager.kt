package com.lcy.practice.manager

import android.widget.TextView
import com.lcy.practice.R
import com.lcy.practice.entity.User
import com.lcy.practice.multiple.MultipleViewHolder
import com.lcy.practice.multiple.QYViewManager

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:39
 * 15708478830@163.com
 **/
class HomeViewManager : QYViewManager<User>() {
    override fun getLayoutResId(layoutCode: Int) = when(layoutCode % 3) {
        0 -> R.layout.manager_layout_1
        1 -> R.layout.manager_layout_2
        2 -> R.layout.manager_layout_3
        else -> R.layout.manager_layout_1
    }

    override fun onBindViewHolder(holder: MultipleViewHolder, position: Int, data: User) {
        val text: TextView? = holder.itemView.findViewById(R.id.text)

        text?.text = data.name
    }
}