package com.readboyi.busalarm.data.database

/**
 * Created by liujiawei on 18-7-6.
 *
 * adb pull /data/data/com.readboyi.busalarm/databases/bus.db
 */
object DBConstant {

    val TABLE_BUS_LISTENER: String = "bus_listener"
    val COLUMN_KEY: String = "key"
    val COLUMN_FROM_STATION: String = "from_station"
    val COLUMN_STATION: String = "station"
    val COLUMN_STATUS: String = "status"

    val TABLE_BUS_STATION: String = "bus_station"
    val COLUMN_ID: String = "id"
    val COLUMN_TIME: String = "time"
    val COLUMN_DESCRIPTION: String = "description"
    val COLUMN_STATION_ID: String = "station_id"
    val COLUMN_LAT: String = "lat"
    val COLUMN_LNG: String = "lng"
    val COLUMN_NAME: String = "name"

    val TABLE_BUS_DIRECT: String = "bus_direct"
    val COLUMN_BUS_KEY: String = "key"
    val COLUMN_BUS_TIME: String = "time"
    val COLUMN_BEGINTIME: String = "beginTime"
    val COLUMN_BUS_Description: String = "description"
    val COLUMN_Direction: String = "direction"
    val COLUMN_EndTime: String = "EndTime"
    val COLUMN_FromStation: String = "FromStation"
    val COLUMN_Id: String = "Id"
    val COLUMN_Interval: String = "Interval"
    val COLUMN_LineNumber: String = "LineNumber"
    val COLUMN_Name: String = "Name"
    val COLUMN_Price: String = "Price"
    val COLUMN_StationCount: String = "StationCount"
    val COLUMN_ToStation: String = "ToStation"
}