package com.lcy.practice.manager

import com.lcy.brick.BaseBrickViewManager
import com.lcy.brick.BrickViewHolder
import com.lcy.practice.R
import com.lcy.practice.databinding.ManagerNewsViewBinding
import com.lcy.practice.entity.New

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:39
 * 15708478830@163.com
 **/
class NewsViewManager : BaseBrickViewManager<New>() {
    override fun getLayoutResId(layoutCode: Int) = R.layout.manager_news_view

    override fun onBindVH(holder: BrickViewHolder, count: Int, position: Int, data: New) {
        val binding = ManagerNewsViewBinding.bind(holder.itemView)
        binding.text.text = data.title
    }
}