package com.lcy.practice.manager

import android.widget.TextView
import com.lcy.practice.R
import com.lcy.practice.entity.New
import com.lcy.practice.multiple.MultipleViewHolder
import com.lcy.practice.multiple.QYViewManager

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:39
 * 15708478830@163.com
 **/
class NewsViewManager : QYViewManager<New>() {
    override fun getLayoutResId(layoutCode: Int) = R.layout.manager_news_view

    override fun onBindViewHolder(holder: MultipleViewHolder, position: Int, data: New) {
        val text: TextView? = holder.itemView.findViewById(R.id.text)

        text?.text = data.title
    }
}