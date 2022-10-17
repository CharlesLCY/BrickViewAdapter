package com.lcy.brick.list

import com.lcy.brick.BrickViewManager


/**
 *
 * <p> Created by CharlesLee on 2022/10/11
 * 15708478830@163.com
 */
fun <T, M : BrickViewManager<T>?> BrickRecyclerView.register(cls: Class<T>, manager: M) {
    adapter.register(cls, manager)
}