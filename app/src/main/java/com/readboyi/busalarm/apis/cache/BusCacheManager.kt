package com.readboyi.busalarm.apis.cache

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.readboyi.busalarm.data.BusDirectBean
import com.readboyi.busalarm.data.BusInfoBean
import com.readboyi.busalarm.data.BusStationsBean
import com.readboyi.busalarm.data.BusStationsListBean
import com.readboyi.busalarm.database.DB
import com.readboyi.busalarm.database.DBConstant

/**
 * Created by liujiawei on 18-7-9.
 */
class BusCacheManager(context: Context?) {

    private var dbRead: SQLiteDatabase? = null
    private var dbWrite: SQLiteDatabase? = null
    private var db: DB? = null

    init {
        if (context != null) {
            db = DB(context)
            dbRead = db?.openDatabase(context.applicationContext)
            dbWrite = db?.openDatabase(context.applicationContext)
        }
    }

    fun close(){
        dbRead?.close()
        dbWrite?.close()
        db?.close()
        dbRead = null
        dbWrite = null
        db = null
    }

    /**
     * 根据id：例如afad-adfd-adsfdff-sdfsdf
     * 缓存对应的站点列表
     */
    fun cacheBusStations(id: String, bean: BusStationsBean){
        val c = dbRead?.query(DBConstant.TABLE_BUS_STATION, null, null, null, null, null, null)
        while (c != null && c.moveToNext()){
            if (c.getString(c.getColumnIndex(DBConstant.COLUMN_ID)) === id) {
                c.close()
                return
            }
        }
        c?.close()
        bean.data.forEach {
            val values = ContentValues()
            values.put(DBConstant.COLUMN_ID, id)
            values.put(DBConstant.COLUMN_TIME, System.currentTimeMillis())
            values.put(DBConstant.COLUMN_DESCRIPTION, it.Description)
            values.put(DBConstant.COLUMN_STATION_ID, it.Id)
            values.put(DBConstant.COLUMN_LAT, it.Lat)
            values.put(DBConstant.COLUMN_LNG, it.Lng)
            values.put(DBConstant.COLUMN_NAME, it.Name)
            dbWrite?.insert(DBConstant.TABLE_BUS_STATION, null, values)
        }
    }

    /**
     * 根据id：例如afad-adfd-adsfdff-sdfsdf
     * 从缓存获取对应的站点列表
     */
    fun queryBusStationsFromCache(id: String): ArrayList<BusStationsListBean>{
        val list: ArrayList<BusStationsListBean> = ArrayList()
        val selection = "${DBConstant.COLUMN_ID}=?"
        val selectionArgs = arrayOf(id)
        val c = dbRead?.query(DBConstant.TABLE_BUS_STATION, null, selection, selectionArgs, null, null, null)
        while (c != null && c.moveToNext()){

            val description: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_DESCRIPTION))
            val Id: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_STATION_ID))
            val Lat: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_LAT))
            val Lng: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_LNG))
            val Name: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_NAME))

            val item = BusStationsListBean(description, Id, Lat, Lng, Name)
            list.add(item)
        }
        c?.close()
        return list
    }

    /**
     * 根据key： 泪如K1
     * 缓存对应的线路
     */
    fun cacheBusDirect(key: String, bean: BusDirectBean){
        val c = dbRead?.query(DBConstant.TABLE_BUS_DIRECT, null, null, null, null, null, null)
        while (c != null && c.moveToNext()){
            if (c.getString(c.getColumnIndex(DBConstant.COLUMN_BUS_KEY)) === key) {
                c.close()
                return
            }
        }
        c?.close()
        bean.data.forEach {
            val values = ContentValues()

            values.put(DBConstant.COLUMN_BUS_KEY, key)
            values.put(DBConstant.COLUMN_BUS_TIME, System.currentTimeMillis())
            values.put(DBConstant.COLUMN_BEGINTIME, it.BeginTime)
            values.put(DBConstant.COLUMN_BUS_Description, it.Description)
            values.put(DBConstant.COLUMN_Direction, it.Direction)
            values.put(DBConstant.COLUMN_EndTime, it.EndTime)
            values.put(DBConstant.COLUMN_FromStation, it.FromStation)
            values.put(DBConstant.COLUMN_Id, it.Id)
            values.put(DBConstant.COLUMN_Interval, it.Interval)
            values.put(DBConstant.COLUMN_LineNumber, it.LineNumber)
            values.put(DBConstant.COLUMN_Name, it.Name)
            values.put(DBConstant.COLUMN_Price, it.Price)
            values.put(DBConstant.COLUMN_StationCount, it.StationCount)
            values.put(DBConstant.COLUMN_ToStation, it.ToStation)

            dbWrite?.insert(DBConstant.TABLE_BUS_DIRECT, null, values)
        }
    }


    /**
     * 根据key： 泪如K1
     * 缓存对应的线路
     */
    fun queryBusDirectFromKey(key: String): ArrayList<BusInfoBean>{

        val list: ArrayList<BusInfoBean> = ArrayList()
        val selection = "${DBConstant.COLUMN_BUS_KEY}=?"
        val selectionArgs = arrayOf(key)
        val c = dbRead?.query(DBConstant.TABLE_BUS_DIRECT, null, selection, selectionArgs, null, null, null)
        while (c != null && c.moveToNext()){

            val beginTime: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_BEGINTIME))
            val description: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_BUS_Description))
            val direction: Int = c.getInt(c.getColumnIndex(DBConstant.COLUMN_Direction))
            val EndTime: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_EndTime))
            val FromStation: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_FromStation))
            val Id: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_Id))
            val Interval: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_Interval))
            val LineNumber: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_LineNumber))
            val Name: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_Name))
            val Price: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_Price))
            val StationCount: Int = c.getInt(c.getColumnIndex(DBConstant.COLUMN_StationCount))
            val ToStation: String = c.getString(c.getColumnIndex(DBConstant.COLUMN_ToStation))

            val item = BusInfoBean(beginTime, description, direction, EndTime, FromStation, Id, Interval, LineNumber, Name, Price, StationCount, ToStation)
            list.add(item)
        }
        c?.close()
        return list
    }
}