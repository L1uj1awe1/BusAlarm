package com.readboyi.busalarm.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.orhanobut.logger.Logger
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import com.readboyi.busalarm.MainActivity
import com.readboyi.busalarm.R

class BusService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        setNotification()
        Log.e("BusService","onBind")
        return BusBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("BusService","onCreate")
    }

    fun setNotification() {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = Notification.Builder(this)
        builder.setContentText("正在监听公交，点击查看详情")//左下角信息
        builder.setContentTitle("公交提醒")//标题右上角
        builder.setWhen(System.currentTimeMillis())
        builder.setSmallIcon(R.drawable.ic_notification)//设置icon，大小有可能会很小
        builder.setTicker("开始监听")//弹出通知时候的信息
        builder.setAutoCancel(false)//false设置点击不消失，true为点击消失
        builder.setOngoing(true)
        //        builder.setDefaults(Notification.DEFAULT_SOUND);//设置默认提醒铃声
        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.setContentIntent(pendingIntent)
        val notification = builder.build()
        startForeground(1, notification)
        nm.notify(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("BusService","onDestroy")
    }

    internal inner class BusBinder: Binder() {

        fun onStartListener() {
            Log.e("BusService","onStartListener")
            Thread(Runnable {

            }).start()
        }
    }
}
