package com.readboyi.busalarm.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.database.BusDBManager

class AddListenerFragment : Fragment() {

    var mBusDBManager: BusDBManager? = null

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
