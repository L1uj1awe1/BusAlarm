package com.readboyi.busalarm.controller.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.readboyi.busalarm.R
import com.readboyi.busalarm.adapter.BusDetailListAdapter
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.data.BusDateManager
import com.readboyi.busalarm.data.bean.*
import com.readboyi.busalarm.data.http.BusHttpManager
import com.readboyi.busalarm.wedget.BusActionBar
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.view_action_bar.*
import java.util.*

class DetailActivity : AppCompatActivity(), BusActionBar.BusActionBarListener, BusDateManager.RequestBusListener, BusHttpManager.BusHttpRequestListener {
    private var timer = Timer()
    private var mAdapter: BusDetailListAdapter? = null
    private var mBusDateManager: BusDateManager? = null
    private var mBusHttpManager: BusHttpManager? = null
    private var mKey: String = ""
    private var mListenStatioo: String = ""
    private var mDirect: ArrayList<BusInfoBean> = arrayListOf()
    private var directIndex: Int = 0
    private var currentBusOnLine: Int = 0
    private var mCurrentDirect: BusInfoBean? = null
    private var currentBusList: ArrayList<BusStatusListBean> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }

    private fun init() {
        mKey = intent.getStringExtra("key")
        mListenStatioo = intent.getStringExtra("station")
        mBusDateManager = BusDateManager(this)
        mBusHttpManager = BusHttpManager(this)
        mBusHttpManager?.listener = this
        mBusDateManager?.listener = this
        bus_action_bar.updateActionBarTitle(Constants.ACTION_BAR_ADD_DETAIL)
        bus_action_bar.listener = this
        btn_change_direct.setOnClickListener {
            directIndex = if (directIndex == 1) 0 else 1
            mCurrentDirect = mDirect[directIndex]
            tv_detail.text = "首班：${mCurrentDirect?.BeginTime}，末班：${mCurrentDirect?.EndTime}，票价：${mCurrentDirect?.Price}"
            tv_from_station.text = mCurrentDirect?.FromStation
            tv_to_station.text = mCurrentDirect?.ToStation
            mBusDateManager?.requestBusStation(mCurrentDirect!!.Id)
        }
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
        mBusDateManager?.requestBusDirect(mKey)
    }

    /**
     * 获取线路状态
     */
    private fun getBusstatus() {
        Thread(Runnable {
            timer.schedule(object : TimerTask() {
                override fun run() {
                    mBusHttpManager?.requestBusStatus(mCurrentDirect!!.Id, mKey, mCurrentDirect!!.FromStation, mListenStatioo)
                }
            }, Constants.REQUEST_STATUS_DELAY, Constants.REQUEST_STATUS_DELAY)
        }).start()
    }

    override fun onRequestBusDirect(direct: ArrayList<BusInfoBean>) {
        mDirect = direct
        mCurrentDirect = mDirect[directIndex]
        tv_detail.text = "首班：${mCurrentDirect?.BeginTime}，末班：${mCurrentDirect?.EndTime}，票价：${mCurrentDirect?.Price}"
        tv_from_station.text = mCurrentDirect?.FromStation
        tv_to_station.text = mCurrentDirect?.ToStation
        mBusDateManager?.requestBusStation(mCurrentDirect!!.Id)
        getBusstatus()
    }

    override fun onRequestBusStation(station: ArrayList<BusStationsListBean>) {
        (mAdapter as BusDetailListAdapter).list = station
        mAdapter?.notifyDataSetChanged()
        list_bus_detail.startLayoutAnimation()
    }


    override fun onBusDirection(bean: BusDirectBean) {}
    override fun onBusStations(bean: BusStationsBean) {}
    override fun onBusStatus(id: String, key: String, bean: BusStatusBean, station: String) {
        currentBusOnLine = bean.data.size
        val title = "${mKey}路(${currentBusOnLine}辆正在运行)"
        bus_action_bar.updateActionBarTitleText(title)
        currentBusList = bean.data
        mAdapter?.setBusStatus(bean.data)
        Log.e("jiajia","----------onBusStatus-")
        bean.data.forEach {
            Log.e("jiajia", it.BusNumber) }
    }

    override fun onClickBarMenu() {}
    override fun onClickBarAdd() {}
    override fun onClickBarBack() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBusDateManager?.onDestroy()
        timer.cancel()
    }

}
