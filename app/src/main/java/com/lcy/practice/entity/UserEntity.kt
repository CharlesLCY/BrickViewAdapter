package com.lcy.practice.entity

import java.io.Serializable

/**
 * Desc:
 * @author Charles Lee
 * create on 2021/8/26 14:29
 * 15708478830@163.com
 **/
data class UserEntity(
    val id: Long,
    val name: String,
    val score: Int
): Serializable