package com.readboyi.busalarm.utils

import android.content.Context
import android.os.PowerManager
import android.util.Log
import com.orhanobut.logger.Logger
import java.util.*

/**
 * Created by liujiawei on 18-7-18.
 */
object CommonUtils {

    /**
     * 获取背景图片
     */
    fun getMenuImageUrl (): String {
        val menuImageUrl = "http://p82xye51n.bkt.clouddn.com/Fighting_"
        val suffix = ".png"
        val index = Math.abs(Random().nextInt()) % 202

        return menuImageUrl + index + suffix
    }

    /**
     * 唤醒屏幕
     */
    fun wakeAndUnlock(context: Context) {
        //获取电源管理器对象
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        val wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright")
        try {
            //点亮屏幕
            Logger.wtf("唤醒屏幕")
            wl.acquire()
            Thread(Runnable {
                Thread.sleep(10 * 1000)
                wl.release()
                Logger.wtf("熄灭屏幕")
            }).start()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            wl.release()
        }
    }
}