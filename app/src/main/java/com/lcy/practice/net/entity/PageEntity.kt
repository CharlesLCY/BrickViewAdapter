package com.lcy.practice.net.entity


/**
 *
 * <p> Created by CharlesLee on 2022/10/11
 * 15708478830@163.com
 */
data class PageEntity<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)