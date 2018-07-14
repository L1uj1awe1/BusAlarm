package com.readboyi.busalarm.controller.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jude.swipbackhelper.SwipeBackHelper
import com.readboyi.busalarm.R

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        SwipeBackHelper.onCreate(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        SwipeBackHelper.onPostCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SwipeBackHelper.onDestroy(this)
    }
}
