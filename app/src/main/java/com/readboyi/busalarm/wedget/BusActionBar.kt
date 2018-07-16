package com.readboyi.busalarm.wedget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_action_bar.view.*

/**
 * Created by liujiawei on 18-7-10.
 */
class BusActionBar(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs), View.OnClickListener {

    interface BusActionBarListener {
        fun onClickBarMenu()
        fun onClickBarAdd()
    }

    var listener: BusActionBarListener? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    private fun init() {
        btn_bar_menu.setOnClickListener(this)
        btn_bar_add.setOnClickListener(this)
    }

    fun updateActionBarTitle(title: String) {
        tv_bar_title.text = title
    }

    override fun onClick(v: View?) {
        when(v){
            btn_bar_menu -> listener?.onClickBarMenu()
            btn_bar_add -> listener?.onClickBarAdd()
        }
    }

}