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
    }

    var density: Float = 0.toFloat()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Logger.addLogAdapter(AndroidLogAdapter())
        getDisplayMetrics()
    }

    private fun getDisplayMetrics() {
        val displayMetrics = resources.displayMetrics
        density = displayMetrics.density
    }

}