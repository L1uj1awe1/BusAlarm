package com.readboyi.busalarm.utils

import com.orhanobut.logger.Logger
import com.readboyi.busalarm.data.bean.BusInfoBean

/**
 * Created by liujiawei on 18-7-18.
 */
object StringUtils {

    fun getLineId(direct: String, directs: ArrayList<BusInfoBean>): String {
        val station = direct.split("-")
        directs.forEach {
            if (it.FromStation == station[0]) {
                Logger.wtf("it.Id = " + it.Id)
                return it.Id
            }
        }
        return ""
    }

}