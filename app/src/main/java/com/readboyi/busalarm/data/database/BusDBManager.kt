package com.readboyi.busalarm.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.readboyi.busalarm.data.bean.BusListenerBean
import java.util.*

class BusDBManager(context: Context?) {

    private var context: Context? = null
    private var dbRead: SQLiteDatabase? = null
    private var dbWrite: SQLiteDatabase? = null
    private var db: BusDB? = null

    init {
        this.context = context
        if (context != null) {
            db = BusDB(context)
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
     * @aim 获取监听列表项
     */
    fun queryListenStations(): java.util.ArrayList<BusListenerBean> {
        val list: ArrayList<BusListenerBean> = ArrayList()
        val c = dbRead?.query(DBConstant.TABLE_BUS_LISTENER, null, null, null, null, null, null)
        try {
            while (c != null && c.moveToNext()){
                val id = c.getString(c.getColumnIndex(DBConstant.COLUMN_ID))
                val key = c.getString(c.getColumnIndex(DBConstant.COLUMN_KEY))
                val fromStation = c.getString(c.getColumnIndex(DBConstant.COLUMN_FROM_STATION))
                val station = c.getString(c.getColumnIndex(DBConstant.COLUMN_STATION))
                val status = c.getInt(c.getColumnIndex(DBConstant.COLUMN_STATUS))
                val stationId = c.getString(c.getColumnIndex(DBConstant.COLUMN_STATION_ID))

                val item = BusListenerBean(id, key, fromStation, station, stationId, status)
                list.add(item)
            }
            c?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            c?.close()
            return list
        }
        return list
    }

    /**
     * @aim 获取服务监听列表项
     */
    fun queryListenServiceStations(): java.util.ArrayList<BusListenerBean> {
        val list: ArrayList<BusListenerBean> = ArrayList()
        val c = dbRead?.query(DBConstant.TABLE_BUS_LISTENER, null, null, null, null, null, null)
        try {
            while (c != null && c.moveToNext()){
                val id = c.getString(c.getColumnIndex(DBConstant.COLUMN_ID))
                val key = c.getString(c.getColumnIndex(DBConstant.COLUMN_KEY))
                val fromStation = c.getString(c.getColumnIndex(DBConstant.COLUMN_FROM_STATION))
                val station = c.getString(c.getColumnIndex(DBConstant.COLUMN_STATION))
                val status = c.getInt(c.getColumnIndex(DBConstant.COLUMN_STATUS))
                val stationId = c.getString(c.getColumnIndex(DBConstant.COLUMN_STATION_ID))

                val item = BusListenerBean(id, key, fromStation, station, stationId, status)
                if (status == 1) {
                    list.add(item)
                }
            }
            c?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            c?.close()
            return list
        }
        return list
    }

    /**
     * @aim 新增监听列表项
     * @param id 线路ID
     * @param buslineId 线路
     * @param station 监听站点
     * @param fromStation 监听站点所属方向
     */
    fun insertListenStation(id: String, key: String, station: String, fromStation: String, stationId: String, status: Int = 1): Boolean{
        val lines = queryListenStations()
        var exist = false
        lines.forEach {
            if(it.stationId == stationId){
                exist = true
                return false
            }
        }
        if(!exist){
            val values = ContentValues()
            values.put(DBConstant.COLUMN_ID, id)
            values.put(DBConstant.COLUMN_KEY, key.toUpperCase(Locale.CHINA))
            values.put(DBConstant.COLUMN_FROM_STATION, fromStation)
            values.put(DBConstant.COLUMN_STATION, station)
            values.put(DBConstant.COLUMN_STATION_ID, stationId)
            values.put(DBConstant.COLUMN_STATUS, status)
            dbWrite?.insert(DBConstant.TABLE_BUS_LISTENER, null, values)
        }
        return true
    }

    /**
     * 删除监听线路
     */
    fun deleteListenLine(id: String,stationId: String){
        dbWrite?.delete(DBConstant.TABLE_BUS_LISTENER
                ,"${DBConstant.COLUMN_ID} = ? and ${DBConstant.COLUMN_STATION_ID} = ?"
                ,arrayOf(id, stationId))
    }


    /**
     * 更新监听状态
     */
    fun updateListenLine(id: String, station: String, status: Int): java.util.ArrayList<BusListenerBean>{
        val values = ContentValues()
        val _status = if (status == 0) 1 else 0
        values.put(DBConstant.COLUMN_STATUS, _status)

        dbWrite?.update(DBConstant.TABLE_BUS_LISTENER
                , values, "${DBConstant.COLUMN_ID} = ? and ${DBConstant.COLUMN_STATION} = ?"
                ,arrayOf(id, station))
        return queryListenStations()
    }

}