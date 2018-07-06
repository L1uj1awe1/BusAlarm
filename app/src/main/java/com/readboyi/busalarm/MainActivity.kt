package com.readboyi.busalarm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.Logger
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.apis.BusHttpRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var request: BusHttpRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        test()
    }

    private fun init() {
        PgyCrashManager.register(this)
    }

    private fun test() {
        request = BusHttpRequest(this)
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

    override fun onDestroy() {
        super.onDestroy()
        PgyCrashManager.unregister()
    }
}
