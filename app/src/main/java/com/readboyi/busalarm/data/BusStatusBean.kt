package com.readboyi.busalarm.data

/**
 * Created by liujiawei on 18-7-6.
 */
data class BusStatusBean (
        val data: ArrayList<BusStatusListBean>,
        val flag: Int
)

data class BusStatusListBean (
        val BusNumber: String,
        val CurrentStation: String,
        val LastPosition: String
)
