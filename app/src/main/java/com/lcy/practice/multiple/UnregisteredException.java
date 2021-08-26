package com.lcy.practice.multiple;

/**
 * 未注册异常信息.
 * <p>
 * Create By 吴荣 at 2018-09-25 15:46.
 */
public class UnregisteredException {

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
    public UnregisteredException(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
