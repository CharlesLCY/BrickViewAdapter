package com.lcy.practice.multiple;

/**
 * 多布局支持接口.
 */
public interface MultipleLayoutSupport {

    /**
     * 获取多布局编号.
     *
     * @return 布局编号, 范围 [0,+ ∞)
     */
    int getMultipleLayoutCode();

}