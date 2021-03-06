package com.readboyi.busalarm.data

/**
 * Created by liujiawei on 18-6-27.
 */
data class BusDirectBean (
        val data: ArrayList<BusInfoBean>,
        val flag: Int
)

data class BusInfoBean (
        val BeginTime: String,
        val Description: String,
        val Direction: Int,
        val EndTime: String,
        val FromStation: String,
        val Id: String,
        val Interval: String,
        val LineNumber: String,
        val Name: String,
        val Price: String,
        val StationCount: Int,
        val ToStation: String
        )
