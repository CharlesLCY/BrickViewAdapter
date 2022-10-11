package com.lcy.practice.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit


/**
 *
 * <p> Created by CharlesLee on 2022/10/11
 * 15708478830@163.com
 */
object HttpClient {
    private const val CONNECTION_TIME_OUT = 10L
    private const val READ_TIME_OUT = 10L
    var API_URL = "https://www.wanandroid.com"

    fun <T> buildApi(service: Class<T>): T {
        return buildRetrofit(API_URL, buildOkHttpClient()).create(service)
    }

    private fun buildOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)
    }

    private fun buildRetrofit(baseUrl: String, builder: OkHttpClient.Builder): Retrofit {
        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }
}