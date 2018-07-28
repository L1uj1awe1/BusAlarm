package com.readboyi.busalarm.data.bean

/**
 * Created by liujiawei on 18-7-6.
 */

data class BusStationsBean (
        val data: ArrayList<BusStationsListBean>,
        val flag: Int
)

data class BusStationsListBean (
        val Description: String,
        val Id: String,
        val Lat: String,
        val Lng: String,
        val Name: String
)

data class BusStationsListBean2 (
        val Description: String,
        val Id: String,
        val Lat: String,
        val Lng: String,
        val Name: String,
        val haveCar: Boolean,
        val busNumber: String
)
