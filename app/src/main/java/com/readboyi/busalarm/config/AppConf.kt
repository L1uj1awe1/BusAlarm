package com.readboyi.busalarm.config
import com.readboyi.busalarm.BuildConfig

/**
 * Created by liujiawei on 18-6-26.
 */
object AppConf {

    val baseUrl: String

    init {
        if (BuildConfig.DEBUG) {
            baseUrl = "http://test.zhbuswx.com/"
        } else {
            baseUrl = "http://www.zhbuswx.com/"
        }
    }

}