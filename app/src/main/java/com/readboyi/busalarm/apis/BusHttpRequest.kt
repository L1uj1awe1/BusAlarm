package com.readboyi.busalarm.apis

import android.content.Context
import com.orhanobut.logger.Logger
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.data.BusDirectBean
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by L1uj1awe1 on 2018/7/6.
 */
class BusHttpRequest(context: Context) {

    var context: Context? = null
    var listener: BusHttpRequestListener? = null

    init {
        this.context = context
    }

    /**
     * 获取公交路线信息
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
                                listener?.onBusDirection(it)
                            }
                    )
        } catch (e: Exception) {
            PgyCrashManager.reportCaughtException(context, e)
        }
    }

    interface BusHttpRequestListener {
        fun onBusDirection(bean: BusDirectBean)
    }

}