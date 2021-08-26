package com.lcy.practice.entity

import com.lcy.practice.multiple.MultipleLayoutSupport
import java.io.Serializable

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:29
 * 15708478830@163.com
 **/
class User : Serializable, MultipleLayoutSupport {
    var name: String = ""
    var age: Int = 0
    var type: Int = 2

    override fun getMultipleLayoutCode() = type
}