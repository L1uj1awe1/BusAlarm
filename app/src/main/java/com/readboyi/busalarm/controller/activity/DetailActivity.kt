package com.readboyi.busalarm.controller.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.readboyi.busalarm.R
import com.readboyi.busalarm.adapter.BusDetailListAdapter
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.data.BusDateManager
import com.readboyi.busalarm.data.bean.BusInfoBean
import com.readboyi.busalarm.data.bean.BusStationsListBean
import com.readboyi.busalarm.wedget.BusActionBar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.view_action_bar.*

class DetailActivity : AppCompatActivity(), BusActionBar.BusActionBarListener, BusDateManager.RequestBusListener {

    private var mAdapter: BusDetailListAdapter? = null
    private var mBusDateManager: BusDateManager? = null
    private var key: String = ""
    private var mListenStatioo: String = ""
    private var mDirect: ArrayList<BusInfoBean> = arrayListOf()
    private var directIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }

    private fun init() {
        key = intent.getStringExtra("key")
        mListenStatioo = intent.getStringExtra("station")
        mBusDateManager = BusDateManager(this)
        mBusDateManager?.listener = this
        bus_action_bar.updateActionBarTitle(Constants.ACTION_BAR_ADD_DETAIL)
        bus_action_bar.listener = this
        initRecyclerView()
    }

    /**
     * 初始化列表
     */
    private fun initRecyclerView () {
        mAdapter = BusDetailListAdapter()
        list_bus_detail.run {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
        mBusDateManager?.requestBusDirect(key)
    }

    override fun onRequestBusDirect(direct: ArrayList<BusInfoBean>) {
        mDirect = direct
        mBusDateManager?.requestBusStation(mDirect[directIndex].Id)
    }
    override fun onRequestBusStation(station: ArrayList<BusStationsListBean>) {
        (mAdapter as BusDetailListAdapter).list = station
        mAdapter?.notifyDataSetChanged()
        list_bus_detail.startLayoutAnimation()
    }

    override fun onClickBarMenu() {}
    override fun onClickBarAdd() {}
    override fun onClickBarBack() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBusDateManager?.onDestroy()
    }

}
