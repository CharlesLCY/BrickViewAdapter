package com.lcy.practice.net


/**
 *
 * <p> Created by CharlesLee on 2022/10/11
 * 15708478830@163.com
 */
class HttpResponse<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String
)