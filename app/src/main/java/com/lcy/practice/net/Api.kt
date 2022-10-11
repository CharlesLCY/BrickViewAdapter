package com.lcy.practice.net

import com.lcy.practice.net.entity.Article
import com.lcy.practice.net.entity.PageEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 *
 * <p> Created by CharlesLee on 2022/10/11
 * 15708478830@163.com
 */
interface Api {
    @GET("article/list/{page}/json")
    suspend fun getHomeList(@Path("page") page: Int, @Query("page_size") pageSize: Int): HttpResponse<PageEntity<Article>>
}