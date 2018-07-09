package com.readboyi.busalarm.data.bean

/**
 * Created by liujiawei on 18-7-9.
 */
data class CacheStationBean(
        val time: Long,
        val stations: ArrayList<BusStationsListBean>
)

data class CacheDirectBean(
        val time: Long,
        val direct: ArrayList<BusInfoBean>
)