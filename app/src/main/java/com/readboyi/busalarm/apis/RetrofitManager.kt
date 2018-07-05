package com.readboyi.busalarm.apis

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
            header("AppType", "Android")
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("randKey", "${System.currentTimeMillis()}")
            header("Device", Build.MODEL)
            header("Version-Name", BuildConfig.VERSION_NAME)
            header("Version-Code",BuildConfig.VERSION_CODE.toString())
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