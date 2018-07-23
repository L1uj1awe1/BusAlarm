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
import android.os.Message
import android.util.Log
import com.readboyi.busalarm.MainActivity
import com.readboyi.busalarm.R
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.controller.activity.TipActivity
import com.readboyi.busalarm.data.bean.BusDirectBean
import com.readboyi.busalarm.data.bean.BusListenerBean
import com.readboyi.busalarm.data.bean.BusStationsBean
import com.readboyi.busalarm.data.bean.BusStatusBean
import com.readboyi.busalarm.data.database.BusDBManager
import com.readboyi.busalarm.data.http.BusHttpManager
import java.util.*
import kotlin.collections.ArrayList
import android.content.ComponentName

class BusService : Service(), BusHttpManager.BusHttpRequestListener {

    private var timer = Timer()
    private var listens: ArrayList<BusListenerBean> = ArrayList()
    private var mBusDBManager: BusDBManager? = null
    private var mBusHttpManager: BusHttpManager? = null

    override fun onBind(intent: Intent): IBinder? {
        Log.e("BusService","onBind")
        init()
        showNotification()
        return BusBinder()
    }

    private fun init () {
        mBusHttpManager = BusHttpManager(this)
        mBusHttpManager?.listener = this
        mBusDBManager = BusDBManager(this)
        listens = mBusDBManager?.queryListenServiceStations() ?: ArrayList()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("BusService","onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBusDirection(bean: BusDirectBean) {}
    override fun onBusStations(bean: BusStationsBean) {}
    override fun onBusStatus(id: String, key: String, bean: BusStatusBean, station: String) {
        bean.data.forEach {
            if (station == it.CurrentStation) {
                val intent = Intent(this, TipActivity::class.java)
                intent.putExtra("id",id)
                intent.putExtra("key",key)
                intent.putExtra("station",station)
//                val cn = ComponentName("com.readboyi.busalarm.controller.activity",
//                        "com.readboyi.busalarm.controller.activity.TipActivity")
//                intent.component = cn
//                intent.action = "android.intent.action.TIP"
                startActivity(intent)
            }
        }
    }

    internal inner class BusBinder: Binder() {

        fun onStartListener() {
            Log.e("BusService","onStartListener")
            Thread(Runnable {
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        listens.forEach {
                            mBusHttpManager?.requestBusStatus(it.id, it.key, it.fromStation, it.station)
                        }
                    }
                }, Constants.REQUEST_LISTEN_DELAY, Constants.REQUEST_LISTEN_DELAY)
            }).start()
        }

        fun onUpdateListens() {
            listens = mBusDBManager?.queryListenServiceStations() ?: ArrayList()
        }
    }

    private fun showNotification() {
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

    private fun cancelNotification() {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelNotification()
        timer.cancel()
        Log.e("BusService","onDestroy")
    }
}
