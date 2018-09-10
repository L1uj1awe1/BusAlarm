package com.readboyi.busalarm.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        btn_pre.setOnClickListener(this)
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
        }
        station_loop.setItems(mStationLoopItems)
        station_loop.setCurrentPosition(0)
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
        direct_loop.setCurrentPosition(0)
        mDirectId = StringUtils.getLineId(mDirectLoopItems[0], mDirects)
        tv_insert_direct.text = "方向：${mDirectLoopItems[0]}"
        mCurrentDirect = mDirectLoopItems[0]
    }

    override fun onClick(v: View?) {

        when(v){
            btn_next -> {
                if (direct_loop.visibility == View.GONE && station_loop.visibility == View.GONE) {
                    if (et_line.text.isEmpty()) {
                        Toast.makeText(context, "线路请勿为空", Toast.LENGTH_LONG).show()
                    } else {
                        mBusDatdManager?.requestBusDirect(et_line.text.toString())
                        tv_insert_direct.visibility = View.VISIBLE
                        btn_pre.visibility = View.VISIBLE
                        et_line.isFocusable = false
                        et_line.isFocusableInTouchMode = false
                    }
                } else if (direct_loop.visibility == View.VISIBLE) {
                    if (mDirectLoopItems.size == 0) {
                        Toast.makeText(context, "未查到线路信息，请尝试更换线路", Toast.LENGTH_LONG).show()
                    } else {
                        tv_insert_station.visibility = View.VISIBLE
                        direct_loop.visibility = View.GONE
                        mBusDatdManager?.requestBusStation(mDirectId)
                        btn_next.text = "确 定"
                    }
                } else {
                    mBusDBManager?.insertListenStation(mDirectId, et_line.text.toString(), mCurrentStation, mCurrentDirect, mStationId)
                    activity?.finish()
                }
            }

            btn_pre -> {
                if (station_loop.visibility == View.VISIBLE) {
                    tv_insert_station.visibility = View.GONE
                    tv_insert_station.text = "监听："
                    station_loop.visibility = View.GONE
                    direct_loop.visibility = View.VISIBLE
                    btn_next.text = "下一步"
                } else if (direct_loop.visibility == View.VISIBLE) {
                    direct_loop.visibility = View.GONE
                    tv_insert_direct.text = "方向："
                    et_line.setText("")
                    et_line.isFocusable = true
                    et_line.isFocusableInTouchMode = true
                    et_line.requestFocus()
                    tv_insert_direct.visibility = View.GONE
                    btn_pre.visibility = View.GONE
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
