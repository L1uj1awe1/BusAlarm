package com.readboyi.busalarm.data.http

import android.content.Context
import android.os.Build
import com.readboyi.busalarm.BuildConfig
import com.readboyi.busalarm.BusApp
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by liujiawei on 18-6-26.
 */
class RetrofitManager {

    val context: Context by lazy { BusApp.INSTANCE.applicationContext }
    private val cacheSize: Long = 10 * 1024 * 1024 // 10MB

    private val okHttp: OkHttpClient


    //设置头
    private var headerInterceptor: Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        chain.proceed(originalRequest.newBuilder().apply {
            header("Host", "www.zhbuswx.com")
            header("Accept", "application/json, text/javascript, */*; q=0.01")
            header("X-Requested-With", "XMLHttpRequest")
            header("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; MI 5s Plus Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044207 Mobile Safari/537.36 MicroMessenger/6.7.2.1340(0x2607023A) NetType/WIFI Language/zh_CN")
            header("Accept-Language", "zh-CN,en-US;q=0.8")
            header("Referer", "http://www.zhbuswx.com/busline/BusQuery.html?v=2.01")
            header("Accept-Encoding", "gzip, deflate")
            header("Cookie", "openid3=oiFDwsus063Z50k7Mxk4UpI79KUA")

            method(originalRequest.method(), originalRequest.body())
        }.build())
    }

    init {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        // 添加公共信息 Header
        okHttpBuilder.addInterceptor(headerInterceptor)
        // 设置网络连接失败时自动重试
        okHttpBuilder.retryOnConnectionFailure(true)
        // 设置连接超时
        okHttpBuilder.connectTimeout(15, TimeUnit.SECONDS)
        // 设置写超时
        okHttpBuilder.writeTimeout(10, TimeUnit.SECONDS)
        // 设置读超时
        okHttpBuilder.readTimeout(10, TimeUnit.SECONDS)
        // 添加 cache
        okHttpBuilder.cache(Cache(context.cacheDir, cacheSize))

        okHttp = okHttpBuilder.build()
    }

    fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttp)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()
    }

    companion object {

        fun get(): RetrofitManager {
            return RetrofitManager()
        }

        val instance: RetrofitManager = get()
    }
}