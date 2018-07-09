package com.readboyi.busalarm.apis

import android.content.Context
import com.readboyi.busalarm.apis.cache.BusCacheManager
import com.readboyi.busalarm.apis.http.BusHttpManager
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.data.*

/**
 * Created by liujiawei on 18-7-9.
 */
class BusDateManager(val context: Context) : BusHttpManager.BusHttpRequestListener {

    /** 判断是否存在缓存 */
    /** 判断缓存是否过期 */
    /** 没过期拿缓存 */
    /** 过期清理缓存 */
    /** 拿网络 */

    interface RequestBusListener {
        fun onRequestBusStation(station: ArrayList<BusStationsListBean>)
        fun onRequestBusDirect(direct: ArrayList<BusInfoBean>)
    }

    var mBusCacheManager: BusCacheManager? = null
    var mBusHttpManager: BusHttpManager? = null
    var listener: RequestBusListener? = null

    init {
        mBusCacheManager = BusCacheManager(context)
        mBusHttpManager = BusHttpManager(context)
        mBusHttpManager?.listener = this
    }

    fun requestBusStation(id: String) {
        val currentTime = System.currentTimeMillis()
        val cacheStation = mBusCacheManager?.queryBusStationsFromCache(id)
        if (cacheStation != null && cacheStation.stations.size != 0) { // 存在缓存
            if (currentTime - cacheStation.time > Constants.CACHE_EXPIRED_TIME) { // 缓存过期
                //删除缓存
                mBusCacheManager?.deleteBusStationsFromCache(id)
                //网络请求
                mBusHttpManager?.requestBusStations(id)
            } else { // 缓存有效
                listener?.onRequestBusStation(cacheStation.stations)
            }
        } else { // 不存在缓存
            //网络请求
            mBusHttpManager?.requestBusStations(id)
        }
    }

    fun requestBusDirect(key: String) {
        val currentTime = System.currentTimeMillis()
        val cacheDirect = mBusCacheManager?.queryBusDirectFromCache(key)
        if (cacheDirect != null && cacheDirect.direct.size != 0) { // 存在缓存
            if (currentTime - cacheDirect.time > Constants.CACHE_EXPIRED_TIME) { // 缓存过期
                //删除缓存
                mBusCacheManager?.deleteBusDirectFromCache(key)
                //网络请求
                mBusHttpManager?.requestBusDirection(key)
            } else { // 缓存有效
                listener?.onRequestBusDirect(cacheDirect.direct)
            }
        } else { // 不存在缓存
            //网络请求
            mBusHttpManager?.requestBusDirection(key)
        }
    }

    fun onDestroy() {
        mBusCacheManager?.close()
        mBusHttpManager?.onDestroy()
    }

    override fun onBusDirection(bean: BusDirectBean) {
        listener?.onRequestBusDirect(bean.data)
    }

    override fun onBusStations(bean: BusStationsBean) {
        listener?.onRequestBusStation(bean.data)
    }

    override fun onBusStatus(bean: BusStatusBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}