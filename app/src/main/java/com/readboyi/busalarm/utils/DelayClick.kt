package com.readboyi.busalarm.utils

/**
 * 防止连续点击
 */
object DelayClick {

    var DELAY_100MS: Long = 100
    var DELAY_300MS: Long = 300
    var DELAY_400MS: Long = 400
    var DELAY_500MS: Long = 500
    var DELAY_800MS: Long = 800
    var DELAY_1100MS: Long = 1100
    var DELAY_1400MS: Long = 1400
    var DELAY_2000MS:Long = 2000

    private var lastClickTime: Long = System.currentTimeMillis()

    fun canClickByTime(delayTime: Long): Boolean {
        var ret = false
        try {
            val now = System.currentTimeMillis()
            val t: Long = now - lastClickTime
            if (t > delayTime || t.toInt() == 0) {
                lastClickTime = now
                ret = true
            }
            lastClickTime = now
        } catch (e: Exception) {
            e.printStackTrace()
            return ret
        }
        return ret
    }

}
