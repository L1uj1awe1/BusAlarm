package com.readboyi.busalarm.apis

import android.content.Context

/**
 * Created by L1uj1awe1 on 2018/7/6.
 */
class BusHttpRequest(context: Context) {

    init {

    }

    fun requestBusDirection (key: String) {
        BusApi.SERVER.getBusDirectionByKey("GetLineListByLineName", key, System.currentTimeMillis())
    }

}