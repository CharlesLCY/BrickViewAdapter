package com.lcy.practice.manager

import com.lcy.brick.BaseBrickViewManager
import com.lcy.brick.BrickViewHolder
import com.lcy.practice.R
import com.lcy.practice.databinding.ManagerArticleLayoutBinding
import com.lcy.practice.net.entity.Article


/**
 *
 * <p> Created by CharlesLee on 2022/10/11
 * 15708478830@163.com
 */
class ArticleManager : BaseBrickViewManager<Article>() {
    override fun getLayoutResId(layoutCode: Int) = R.layout.manager_article_layout

    override fun getClickViewId(): MutableList<Int> = mutableListOf(
        R.id.title
    )

    override fun getLongClickViewId(): MutableList<Int> = mutableListOf(
        R.id.title
    )

    override fun onBindVH(holder: BrickViewHolder, count: Int, position: Int, data: Article) {
        val binding = ManagerArticleLayoutBinding.bind(holder.itemView)
        binding.title.text = data.title
        binding.desc.text = data.niceDate
    }
}