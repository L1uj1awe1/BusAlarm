package com.readboyi.busalarm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.R
import com.readboyi.busalarm.apis.BusHttpRequest
import kotlinx.android.synthetic.main.fragment_bus_listener.*

class BusListenerFragment : Fragment() {

    var request: BusHttpRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bus_listener, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        test()
    }


    private fun init() {
        PgyCrashManager.register(activity)
    }

    private fun test() {
        request = BusHttpRequest(context)
        btn_test.setOnClickListener{
            request?.requestBusDirection("8")
        }
        btn_test_2.setOnClickListener {
            request?.requestBusStations("9306df04-e47a-4d19-b00c-8acca26d900d")
        }
        btn_test_3.setOnClickListener {
            request?.requestBusStatus("8", "拱北口岸总站")
        }
    }

    companion object {

        fun newInstance(): BusListenerFragment {
            val fragment = BusListenerFragment()
            return fragment
        }
    }
}
