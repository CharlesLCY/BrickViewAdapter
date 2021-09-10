package com.lcy.practice.manager

import android.widget.TextView
import com.lcy.practice.R
import com.lcy.practice.entity.New
import com.lcy.brick.BrickViewHolder
import com.lcy.brick.BaseBrickViewManager

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:39
 * 15708478830@163.com
 **/
class NewsViewManager : com.lcy.brick.BaseBrickViewManager<New>() {
    override fun getLayoutResId(layoutCode: Int) = R.layout.manager_news_view

    override fun onBindVH(holder: com.lcy.brick.BrickViewHolder, count: Int, position: Int, data: New) {
        val text: TextView? = holder.itemView.findViewById(R.id.text)

        text?.text = data.title
    }
}