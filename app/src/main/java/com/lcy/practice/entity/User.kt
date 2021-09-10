package com.lcy.practice.entity

import com.lcy.brick.BrickViewSupport
import java.io.Serializable

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:29
 * 15708478830@163.com
 **/
class User : Serializable, com.lcy.brick.BrickViewSupport {
    var name: String = ""
    var age: Int = 0
    var type: Int = 2

    override val layoutCode: Int
        get() = type

    override fun toString(): String {
        return "User(name='$name', age=$age, type=$type, layoutCode=$layoutCode)"
    }
}