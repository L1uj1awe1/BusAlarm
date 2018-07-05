package com.readboyi.busalarm

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by liujiawei on 18-6-26.
 */
class BusApp : Application() {

    companion object {
        lateinit var INSTANCE: BusApp
        var token: String = ""
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Logger.addLogAdapter(AndroidLogAdapter())
    }

}