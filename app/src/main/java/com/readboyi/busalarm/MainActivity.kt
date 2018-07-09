package com.readboyi.busalarm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.controller.fragment.BusListenerListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initFragment()
    }

    private fun init() {
        PgyCrashManager.register(this)
    }

    private fun initFragment() {
        val busListenerFragment = BusListenerListFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fl_bus_listener_layout, busListenerFragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        PgyCrashManager.unregister()
    }
}
