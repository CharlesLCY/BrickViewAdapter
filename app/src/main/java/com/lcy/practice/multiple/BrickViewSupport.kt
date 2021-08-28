package com.lcy.practice.multiple

/**
 * 多布局支持接口，要设置多布局的ViewManager对应的实体类需继承该接口
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 **/
interface BrickViewSupport {
    /**
     * 获取多布局编号.
     *
     * @return 布局编号, 范围 [0,+ ∞)
     */
    val layoutCode: Int
}