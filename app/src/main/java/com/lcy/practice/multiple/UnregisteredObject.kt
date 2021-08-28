package com.lcy.practice.multiple;

/**
 * <Desc> 未注册异常信息.
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
 */
public class UnregisteredObject {

    /**
     * 标题.
     */
    public String title;

    /**
     * 消息.
     */
    public String message;

    /**
     * 构造器
     *
     * @param title   标题
     * @param message 消息
     */
    public UnregisteredObject(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
