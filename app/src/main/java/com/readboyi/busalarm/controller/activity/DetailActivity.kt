package com.readboyi.busalarm.controller.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.jude.swipbackhelper.SwipeBackHelper
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
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity(), BusActionBar.BusActionBarListener, BusDateManager.RequestBusListener, BusHttpManager.BusHttpRequestListener {
    private var timer = Timer()
    private var mAdapter: BusDetailListAdapter? = null
    private var mBusDateManager: BusDateManager? = null
    private var mBusHttpManager: BusHttpManager? = null
    private var mKey: String = ""
    private var mListenStatioo: String = ""
    private var mDirect: ArrayList<BusInfoBean> = arrayListOf()
    private var directIndex: Int = 0
    private var mCurrentDirect: BusInfoBean? = null
    private var mStation: ArrayList<BusStationsListBean> = arrayListOf()
    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        SwipeBackHelper.onCreate(this)
        init()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        SwipeBackHelper.onPostCreate(this)
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
        timer.cancel()
        timer = Timer()
        mBusHttpManager?.requestBusStatus(mCurrentDirect!!.Id, mKey, mCurrentDirect!!.FromStation, mListenStatioo)
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
    }

    override fun onRequestBusStation(station: ArrayList<BusStationsListBean>) {
        mStation = station
        getBusstatus()
    }


    override fun onBusDirection(bean: BusDirectBean) {}
    override fun onBusStations(bean: BusStationsBean) {}
    override fun onBusStatus(id: String, key: String, bean: BusStatusBean, station: String) {
        val list = ArrayList<BusStationsListBean2>()
        val busNumbers = ArrayList<String>()
        mStation.forEach {
            val s = it
            var inserted = false
            bean.data.forEach {
                if (s.Name == it.CurrentStation && !checkBusNumber(busNumbers, it.BusNumber)) {
                    busNumbers.add(it.BusNumber)
                    list.add(BusStationsListBean2(s.Description, s.Id, s.Lat, s.Lng, s.Name, true, it.BusNumber))
                    inserted = true
                }
            }
            if (!inserted) {
                list.add(BusStationsListBean2(s.Description, s.Id, s.Lat, s.Lng, s.Name, false, ""))
            }
        }
        mHandler.post {
            bus_action_bar.updateActionBarTitleText("${mKey}路(${bean.data.size}辆正在运行)")
            mAdapter?.update(list)
        }
    }

    fun checkBusNumber(list: ArrayList<String>, busNumber: String): Boolean {
        list.forEach {
          if (it == busNumber) {
              return true
          }
        }
        return false
    }

    override fun onClickBarMenu() {}
    override fun onClickBarAdd() {}
    override fun onClickBarBack() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBusDateManager?.onDestroy()
        mBusHttpManager?.onDestroy()
        timer.cancel()
        SwipeBackHelper.onDestroy(this)
    }

}
