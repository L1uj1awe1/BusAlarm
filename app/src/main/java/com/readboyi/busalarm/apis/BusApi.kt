package com.readboyi.busalarm.apis

import com.readboyi.busalarm.config.AppConf
import com.readboyi.busalarm.data.BusDirectBean
import io.reactivex.Observable
import retrofit2.http.*

interface LiveService {

//    /**
//     * 发红包
//     */
//    @POST("v2/webapi/lesson/redpacket/publish")
//    fun publishRedPacket(
//            @Query("lesson_id") lesson_id: String,
//            @Query("type") type: String,
//            @Query("total_score") total_score: Int,
//            @Query("each_score") each_score: Int,
//            @Query("count") count: Int
//    ): Observable<BusDirectBean>
}

object LiveApi {
    val server: LiveService = RetrofitManager.instance
            .getRetrofit(AppConf.baseUrl)
            .create(LiveService::class.java)
}