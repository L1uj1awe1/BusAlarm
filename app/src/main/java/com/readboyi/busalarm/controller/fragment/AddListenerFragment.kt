package com.readboyi.busalarm.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.BusDateManager
import com.readboyi.busalarm.data.bean.BusInfoBean
import com.readboyi.busalarm.data.bean.BusStationsListBean
import com.readboyi.busalarm.data.database.BusDBManager
import com.readboyi.busalarm.utils.StringUtils
import kotlinx.android.synthetic.main.fragment_add_listener.*

class AddListenerFragment : Fragment(), View.OnClickListener, BusDateManager.RequestBusListener {

    private var mBusDBManager: BusDBManager? = null
    private var mBusDatdManager: BusDateManager? = null
    private var mDirectLoopItems = ArrayList<String>()
    private var mStationLoopItems = ArrayList<String>()
    private var mDirects: ArrayList<BusInfoBean> = ArrayList()
    private var mStations: ArrayList<BusStationsListBean> = ArrayList()
    private var mStationId = ""
    private var mDirectId = ""
    private var mCurrentStation = ""
    private var mCurrentDirect = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_listener, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mBusDBManager = BusDBManager(context)
        mBusDatdManager = BusDateManager(context)
        mBusDatdManager?.listener = this
        btn_next.setOnClickListener(this)
        initDirectLoop()
        initStationLoop()
    }

    private fun initDirectLoop() {
        direct_loop.setNotLoop()
        direct_loop.setInitPosition(0)
        direct_loop.setListener { index ->
            mDirectId = StringUtils.getLineId(mDirectLoopItems[index], mDirects)
            tv_insert_direct.text = "方向：${mDirectLoopItems[index]}"
            mCurrentDirect = mDirectLoopItems[index]
        }
    }

    private fun initStationLoop() {
        station_loop.setNotLoop()
        station_loop.setInitPosition(0)
        station_loop.setListener { index ->
            mCurrentStation = mStationLoopItems[index]
            tv_insert_station.text = "监听：${mStationLoopItems[index]}"
            mStationId = StringUtils.getStationId(mStationLoopItems[index], mStations)
        }
    }

    override fun onRequestBusStation(station: ArrayList<BusStationsListBean>) {
        mStations = station
        station_loop.visibility = View.VISIBLE
        mStationLoopItems.clear()
        station.forEach {
            mStationLoopItems.add(it.Name)
            Log.e("jiajia",it.Name)
        }
        station_loop.setItems(mStationLoopItems)
        mCurrentStation = mStationLoopItems[0]
        tv_insert_station.text = "监听：${mStationLoopItems[0]}"
        mStationId = StringUtils.getStationId(mStationLoopItems[0], mStations)
    }

    override fun onRequestBusDirect(direct: ArrayList<BusInfoBean>) {
        mDirects = direct
        direct_loop.visibility = View.VISIBLE
        mDirectLoopItems.clear()
        direct.forEach {
            mDirectLoopItems.add(it.FromStation + "-" + it.ToStation)
        }
        direct_loop.setItems(mDirectLoopItems)
        mDirectId = StringUtils.getLineId(mDirectLoopItems[0], mDirects)
        tv_insert_direct.text = "方向：${mDirectLoopItems[0]}"
        mCurrentDirect = mDirectLoopItems[0]
    }

    override fun onClick(v: View?) {
        when(v){
            btn_next -> {
                if (et_line.visibility == View.VISIBLE) {
                    et_line.visibility = View.GONE
                    mBusDatdManager?.requestBusDirect(et_line.text.toString())
                    tv_insert_key.text = "线路：${et_line.text}"
                } else if (direct_loop.visibility == View.VISIBLE) {
                    direct_loop.visibility = View.GONE
                    mBusDatdManager?.requestBusStation(mDirectId)
                    btn_next.text = "确定"
                } else {
                    mBusDBManager?.insertListenStation(et_line.text.toString(), mCurrentStation, mCurrentDirect, mStationId)
                    activity?.finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBusDBManager?.close()
    }

    companion object {

        fun newInstance(): AddListenerFragment {
            val fragment = AddListenerFragment()
            return fragment
        }
    }
}
