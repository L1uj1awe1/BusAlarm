package com.readboyi.busalarm.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.readboyi.busalarm.data.cache.BusCacheManager
import com.readboyi.busalarm.data.http.BusHttpManager
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.data.bean.*

/**
 * Created by liujiawei on 18-7-9.
 */
class BusDateManager(val context: Context?) : BusHttpManager.BusHttpRequestListener {

    /** 判断是否存在缓存 */
    /** 判断缓存是否过期 */
    /** 没过期拿缓存 */
    /** 过期清理缓存 */
    /** 拿网络 */

    interface RequestBusListener {
        fun onRequestBusStation(station: ArrayList<BusStationsListBean>)
        fun onRequestBusDirect(direct: ArrayList<BusInfoBean>)
    }

    private var mBusCacheManager: BusCacheManager? = null
    private var mBusHttpManager: BusHttpManager? = null
    var listener: RequestBusListener? = null
    private val mHandler = Handler(Looper.getMainLooper())

    init {
        mBusCacheManager = BusCacheManager(context)
        mBusHttpManager = BusHttpManager(context)
        mBusHttpManager?.listener = this
    }

    /**
     * 请求站点数据
     */
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

    /**
     * 请求方向线路数据
     */
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
        mHandler.post {
            listener?.onRequestBusDirect(bean.data)
        }
    }

    override fun onBusStations(bean: BusStationsBean) {
        mHandler.post {
            listener?.onRequestBusStation(bean.data)
        }
    }

    override fun onBusStatus(bean: BusStatusBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}