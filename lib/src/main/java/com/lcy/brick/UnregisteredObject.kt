package com.lcy.brick

import java.io.Serializable

/**
 * 未注册异常信息.
 * Created by CharlesLee on 2021/8/27
 * 15708478830@163.com
</Desc> */
class UnregisteredObject(
    /**
     * 标题.
     */
    var title: String,
    /**
     * 消息.
     */
    var message: String
): Serializable