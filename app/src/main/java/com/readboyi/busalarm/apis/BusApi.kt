package com.readboyi.busalarm.apis

import com.readboyi.busalarm.config.AppConf
import com.readboyi.busalarm.data.BusDirectBean
import io.reactivex.Observable
import retrofit2.http.*

interface BusService {

    /**
     * @aim 根据公交号码获取路线信息
     * @param handlerName GetLineListByLineName
     * @param key 公交号码，例如：8路
     * @param _ 时间戳 System.currentTimeMillis()
     */
    @POST("Handlers/BusQuery.ashx")
    fun getBusDirectionByKey(
            @Query("handlerName") handlerName: String,
            @Query("key") key: String,
            @Query("_") timestamp: Long
    ): Observable<BusDirectBean>
}

object BusApi {
    val SERVER: BusService = RetrofitManager.instance
            .getRetrofit(AppConf.baseUrl)
            .create(BusService::class.java)
}