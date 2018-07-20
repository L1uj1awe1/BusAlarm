package com.readboyi.busalarm.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

/**
 * Created by liujiawei on 18-7-20.
 */
class BusServiceManager(private val context: Context) {

    private var mBusBinder: BusService.BusBinder? = null

    fun bindService(){
        val bindIntent = Intent(context, BusService::class.java)
        context.bindService(bindIntent, connection, Context.BIND_AUTO_CREATE)
    }

    fun updateListens() {
        mBusBinder?.onUpdateListens()
    }

    fun unbindService(){
        context.unbindService(connection)
    }

    private val connection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {}

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBusBinder = service as BusService.BusBinder
            mBusBinder?.onStartListener()
        }
    }

}