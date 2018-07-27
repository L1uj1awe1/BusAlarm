package com.readboyi.busalarm.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readboyi.busalarm.R
import com.readboyi.busalarm.adapter.BusListenerAdapter
import com.readboyi.busalarm.data.bean.BusListenerBean
import com.readboyi.busalarm.data.database.BusDBManager
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.fragment_bus_listener.*
import java.util.ArrayList

class BusListenerListFragment : Fragment() {

    private var mBusDBManager: BusDBManager? = null
    private var mAdapter: BusListenerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bus_listener, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        notifyDataChange()
    }

    private fun init() {
        mBusDBManager = BusDBManager(context)
        initRecyclerView()
    }

    /**
     * 初始化列表
     */
    private fun initRecyclerView () {
        mAdapter = BusListenerAdapter()
        list_bus_listener.run {
            isItemViewSwipeEnabled = true
            setOnItemMoveListener(object : OnItemMoveListener{
                override fun onItemMove(srcHolder: RecyclerView.ViewHolder?, targetHolder: RecyclerView.ViewHolder?): Boolean {
                    return false
                }
                override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) {
                    mAdapter?.swipeDeleteItem(srcHolder?.adapterPosition ?: -1)
                    mAdapter?.notifyItemRemoved(srcHolder?.adapterPosition ?: -1)
                }
            })
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
    }

    /**
     * 更新列表
     */
    fun notifyDataChange (){
        val list: ArrayList<BusListenerBean> = mBusDBManager?.queryListenStations() ?: ArrayList<BusListenerBean>()
        (mAdapter as BusListenerAdapter).list = list
        (mAdapter as BusListenerAdapter).mBusDBManager = mBusDBManager
        mAdapter?.notifyDataSetChanged()
        list_bus_listener.startLayoutAnimation()
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
