package com.readboyi.busalarm.apis

import com.readboyi.busalarm.config.AppConf
import com.readboyi.busalarm.data.BusDirectBean
import com.readboyi.busalarm.data.BusStationsBean
import com.readboyi.busalarm.data.BusStatusBean
import io.reactivex.Observable
import retrofit2.http.*

interface BusService {

    /**
     * @aim 根据公交号码获取路线信息
     * @param handlerName GetLineListByLineName
     * @param key 公交号码，例如：8路
     * @param _ 时间戳 System.currentTimeMillis()
     */
    @GET("Handlers/BusQuery.ashx")
    fun getBusDirectionByKey(
            @Query("handlerName") handlerName: String,
            @Query("key") key: String,
            @Query("_") timestamp: Long
    ): Observable<BusDirectBean>

    /**
     * @aim 根据公交路线id获取站点信息
     * @param id 交路线id 由getBusDirectionByKey接口获得
     * @param _ 时间戳 System.currentTimeMillis()
     */
    @GET("StationList/GetStationList")
    fun getBusStationsById(
            @Query("id") id: String,
            @Query("_") timestamp: Long
    ): Observable<BusStationsBean>

    @GET("RealTime/GetRealTime")
    fun getBusStatusByLine (
            @Query("id") id: String,
            @Query("fromStation") fromStation: String,
            @Query("_") timestamp: Long
    ): Observable<BusStatusBean>
}

object BusApi {
    val SERVER: BusService = RetrofitManager.instance
            .getRetrofit(AppConf.baseUrl)
            .create(BusService::class.java)
}