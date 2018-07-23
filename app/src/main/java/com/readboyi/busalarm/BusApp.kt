package com.readboyi.busalarm

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.service.BusServiceManager
import com.readboyi.busalarm.utils.CommonUtils

/**
 * Created by liujiawei on 18-6-26.
 */
class BusApp : Application() {

    companion object {
        lateinit var INSTANCE: BusApp
    }

    var density: Float = 0.toFloat()
    var menuImageUrl: String = ""
    var service: BusServiceManager? = null

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        PgyCrashManager.register(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        getDisplayMetrics()
        getMenuImage()

        service = BusServiceManager(this)
        service?.bindService()
    }

    override fun onTerminate() {
        super.onTerminate()
        service?.unbindService()
        PgyCrashManager.unregister()
    }

    private fun getDisplayMetrics() {
        val displayMetrics = resources.displayMetrics
        density = displayMetrics.density
    }

    private fun getMenuImage(){
        menuImageUrl = CommonUtils.getMenuImageUrl()
    }

}