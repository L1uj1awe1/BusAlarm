package com.readboyi.busalarm.controller.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.readboyi.busalarm.R
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.wedget.BusActionBar
import kotlinx.android.synthetic.main.view_action_bar.*

class DetailActivity : AppCompatActivity(), BusActionBar.BusActionBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }

    private fun init() {
        bus_action_bar.updateActionBarTitle(Constants.ACTION_BAR_ADD_DETAIL)
        bus_action_bar.listener = this
    }

    override fun onClickBarMenu() {}
    override fun onClickBarAdd() {}
    override fun onClickBarBack() {
        finish()
    }

}
