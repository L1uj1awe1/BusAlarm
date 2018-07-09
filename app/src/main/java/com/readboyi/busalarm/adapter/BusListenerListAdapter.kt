package com.readboyi.busalarm.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.BusListenerBean
import com.readboyi.busalarm.database.BusDBManager
import kotlinx.android.synthetic.main.adapter_bus_listener.view.*

/**
 * Created by liujiawei on 18-6-28.
 */
class BusListenerAdapter : RecyclerView.Adapter<BusListenerAdapter.RedPacketHolder>() {

    var mBusDBManager: BusDBManager? = null
    var list: ArrayList<BusListenerBean> = arrayListOf()

    override fun onBindViewHolder(holder: RedPacketHolder, position: Int) {
        val bean = list[position]
        holder.tvBusKey.text = bean.key
        holder.tvFromStation.text = bean.fromStation
        holder.tvStation.text = bean.station
        holder.tvStatus.text = bean.status.toString()

        holder.itemView.setOnClickListener {
            list = mBusDBManager?.updateListenLine(bean) ?: arrayListOf()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedPacketHolder{
        return RedPacketHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_bus_listener, parent, false))
    }

    inner class RedPacketHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvBusKey: TextView = view.tv_bus_key
        var tvFromStation: TextView = view.tv_from_station
        var tvStation: TextView = view.tv_station
        var tvStatus: TextView = view.tv_status
    }
}