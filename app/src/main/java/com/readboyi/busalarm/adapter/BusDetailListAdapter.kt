package com.readboyi.busalarm.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.bean.BusStationsListBean2
import kotlinx.android.synthetic.main.adapter_detail.view.*

/**
 * Created by liujiawei on 18-6-28.
 */
class BusDetailListAdapter : RecyclerView.Adapter<BusDetailListAdapter.DetailHolder>() {

    private var list: ArrayList<BusStationsListBean2> = arrayListOf()

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        val bean = list[position]
        holder.tv_station.text = bean.Name
        holder.tv_index.text = (position + 1).toString()
        if (bean.haveCar) {
            holder.tv_bus_number.text = bean.busNumber
            holder.tv_bus_number.visibility = View.VISIBLE
        } else {
            holder.tv_bus_number.visibility = View.GONE
        }
    }

    fun update(list: ArrayList<BusStationsListBean2>) {
        this.list.clear()
        notifyDataSetChanged()
        this.list = list
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