package com.readboyi.busalarm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readboyi.busalarm.R
import com.readboyi.busalarm.adapter.BusListenerAdapter
import com.readboyi.busalarm.data.BusListenerBean
import com.readboyi.busalarm.database.BusDBManager
import kotlinx.android.synthetic.main.fragment_bus_listener.*
import java.util.ArrayList

class BusListenerListFragment : Fragment() {

    var mBusDBManager: BusDBManager? = null

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
        mBusDBManager = BusDBManager(context)

        list_bus_listener.run {
            adapter = BusListenerAdapter()
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun getListFromDatabase () {
        mBusDBManager?.insertListenStation("8", "珠海拱北口岸", "明珠中", 1)
        val list: ArrayList<BusListenerBean> = mBusDBManager?.queryListenStations() ?: ArrayList<BusListenerBean>()
        notifyDataChange(list)
    }

    fun notifyDataChange (list: ArrayList<BusListenerBean>){
        (list_bus_listener.adapter as BusListenerAdapter).list = list
        (list_bus_listener.adapter as BusListenerAdapter).mBusDBManager = mBusDBManager
        list_bus_listener.adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBusDBManager?.close()
    }

    companion object {

        fun newInstance(): BusListenerListFragment {
            val fragment = BusListenerListFragment()
            return fragment
        }
    }
}
