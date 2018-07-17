package com.readboyi.busalarm.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.BusDateManager
import com.readboyi.busalarm.data.bean.BusInfoBean
import com.readboyi.busalarm.data.bean.BusStationsListBean
import com.readboyi.busalarm.data.database.BusDBManager
import kotlinx.android.synthetic.main.fragment_add_listener.*

class AddListenerFragment : Fragment(), View.OnClickListener, BusDateManager.RequestBusListener {

    private var mBusDBManager: BusDBManager? = null
    private var mBusDatdManager: BusDateManager? = null
    private var mDirectLoopItems = ArrayList<String>()
    private var mStationLoopItems = ArrayList<String>()
    private var mDirects: ArrayList<BusInfoBean> = ArrayList()
    private var mStations: ArrayList<BusStationsListBean> = ArrayList()
    private var mDirectId = ""

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
            Logger.wtf(mDirectLoopItems[index])
        }
    }

    private fun initStationLoop() {
        station_loop.setNotLoop()
        station_loop.setInitPosition(0)
        //        direct_loop.setItems(list)
        station_loop.setListener { index ->

        }
    }

    override fun onRequestBusStation(station: ArrayList<BusStationsListBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestBusDirect(direct: ArrayList<BusInfoBean>) {
        mDirects = direct
        direct_loop.visibility = View.VISIBLE
        mDirectLoopItems.clear()
        direct.forEach {
            mDirectLoopItems.add(it.FromStation + "-" + it.ToStation)
        }
        direct_loop.setItems(mDirectLoopItems)
    }

    override fun onClick(v: View?) {
        when(v){
            btn_next -> {
                if (et_line.visibility == View.VISIBLE) {
                    et_line.visibility = View.GONE
                    mBusDatdManager?.requestBusDirect(et_line.text.toString())
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
