package com.readboyi.busalarm.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.bean.BusStationsListBean
import com.readboyi.busalarm.data.bean.BusStatusListBean
import kotlinx.android.synthetic.main.adapter_detail.view.*

/**
 * Created by liujiawei on 18-6-28.
 */
class BusDetailListAdapter : RecyclerView.Adapter<BusDetailListAdapter.DetailHolder>() {

    var list: ArrayList<BusStationsListBean> = arrayListOf()
    var status: ArrayList<BusStatusListBean> = arrayListOf()

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {

        val bean = list[position]
        holder.tv_station.text = bean.Name
        holder.tv_index.text = position.toString()
        status.forEach {
            if (it.CurrentStation == bean.Name) {
                holder.tv_bus_number.visibility = View.VISIBLE
                holder.tv_bus_number.text = it.BusNumber
            }
        }
    }

    fun setBusStatus(statusList: ArrayList<BusStatusListBean>) {
        status = statusList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder{
        return DetailHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_detail, parent, false))
    }

    inner class DetailHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_station: TextView = view.tv_station
        var tv_index: TextView = view.tv_index
        var tv_bus_number: TextView = view.tv_bus_number
    }
}