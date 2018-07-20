package com.readboyi.busalarm.data.bean

/**
 * Created by liujiawei on 18-7-6.
 */
data class BusListenerBean (
        val id: String,
        val key: String,
        val fromStation: String,
        val station: String,
        val stationId: String,
        val status: Int
)