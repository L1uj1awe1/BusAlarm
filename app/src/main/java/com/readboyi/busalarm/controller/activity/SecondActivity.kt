package com.readboyi.busalarm.controller.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jude.swipbackhelper.SwipeBackHelper
import com.readboyi.busalarm.R
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.wedget.BusActionBar
import kotlinx.android.synthetic.main.view_action_bar.*

class SecondActivity : AppCompatActivity(), BusActionBar.BusActionBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        SwipeBackHelper.onCreate(this)
        initView()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        SwipeBackHelper.onPostCreate(this)
    }

    private fun initView() {
        bus_action_bar.updateActionBarTitle(intent.getIntExtra("type", -1))
        bus_action_bar.listener = this
    }

    override fun onClickBarMenu() {}

    override fun onClickBarBack() {
        finish()
    }

    override fun onClickBarAdd() {}

    override fun onDestroy() {
        super.onDestroy()
        SwipeBackHelper.onDestroy(this)
    }
}
