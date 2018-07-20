package com.readboyi.busalarm.data.http

import android.content.Context
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.data.cache.BusCacheManager
import com.readboyi.busalarm.data.bean.BusDirectBean
import com.readboyi.busalarm.data.bean.BusStationsBean
import com.readboyi.busalarm.data.bean.BusStatusBean
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by L1uj1awe1 on 2018/7/6.
 */
class BusHttpManager(context: Context?) {

    var context: Context? = null
    var listener: BusHttpRequestListener? = null
    var mBusCacheManager: BusCacheManager? = null

    init {
        this.context = context
        mBusCacheManager = BusCacheManager(context)
    }

    /**
     * 根据公交号码，获取公交路线列表
     */
    fun requestBusDirection (key: String) {
        try {
            BusApi.SERVER.getBusDirectionByKey("GetLineListByLineName", key, System.currentTimeMillis())
                    .doOnSubscribe {}
                    .retry(2)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy (
                            onError = {
                                it.printStackTrace()
                            },
                            onNext = {
                                mBusCacheManager?.cacheBusDirect(key, it)
                                listener?.onBusDirection(it)
                            }
                    )
        } catch (e: Exception) {
            e.printStackTrace()
            PgyCrashManager.reportCaughtException(context, e)
        }
    }

    /**
     * 根据公交线路id，请求公交站点列表
     */
    fun requestBusStations (id: String) {
        try {
            BusApi.SERVER.getBusStationsById(id, System.currentTimeMillis())
                    .doOnSubscribe {}
                    .retry(2)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy (
                            onError = {
                                it.printStackTrace()
                            },
                            onNext = {
                                mBusCacheManager?.cacheBusStations(id, it)
                                listener?.onBusStations(it)
                            }
                    )
        } catch (e: Exception) {
            e.printStackTrace()
            PgyCrashManager.reportCaughtException(context, e)
        }
    }

    /**
     * 根据公交号码、起始站点，获取当前路线车辆到达状况
     */
    fun requestBusStatus (id: String, key: String, fromStation: String, station: String) {
        try {
            BusApi.SERVER.getBusStatusByLine(key, fromStation, System.currentTimeMillis())
                    .doOnSubscribe {}
                    .retry(2)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy (
                            onError = {
                                it.printStackTrace()
                            },
                            onNext = {
                                listener?.onBusStatus(id, it, station)
                            }
                    )
        } catch (e: Exception) {
            e.printStackTrace()
            PgyCrashManager.reportCaughtException(context, e)
        }
    }

    fun onDestroy() {
        mBusCacheManager?.close()
    }

    interface BusHttpRequestListener {
        fun onBusDirection(bean: BusDirectBean)
        fun onBusStations(bean: BusStationsBean)
        fun onBusStatus(id: String, bean: BusStatusBean, station: String)
    }

}