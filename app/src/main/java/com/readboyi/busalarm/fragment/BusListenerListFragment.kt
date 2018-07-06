package com.readboyi.busalarm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readboy.educationmanager.adapter.BusListenerAdapter
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.BusListenerBean
import kotlinx.android.synthetic.main.fragment_bus_listener.*
import java.util.ArrayList

class BusListenerListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bus_listener, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getListFromDatabase()
    }


    private fun init() {
        list_bus_listener.adapter = BusListenerAdapter()
        list_bus_listener.layoutManager = LinearLayoutManager(context)
        list_bus_listener.itemAnimator = DefaultItemAnimator()
        list_bus_listener.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun getListFromDatabase () {
        //读取数据库
        val l = ArrayList<BusListenerBean>()
        l.add(BusListenerBean("8", "珠海拱北口岸", "明珠中", false))
        l.add(BusListenerBean("8", "珠海拱北口岸", "明珠中", false))
        l.add(BusListenerBean("8", "珠海拱北口岸", "明珠中", false))
        l.add(BusListenerBean("8", "珠海拱北口岸", "明珠中", false))

        notifyDataChange(l)
    }

    fun notifyDataChange (list: ArrayList<BusListenerBean>){
        (list_bus_listener.adapter as BusListenerAdapter).list = list
        list_bus_listener.adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关闭数据库
    }

    companion object {

        fun newInstance(): BusListenerListFragment {
            val fragment = BusListenerListFragment()
            return fragment
        }
    }
}
