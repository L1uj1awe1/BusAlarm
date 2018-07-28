package com.readboyi.busalarm.wedget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.readboyi.busalarm.config.Constants
import kotlinx.android.synthetic.main.view_action_bar.view.*

/**
 * Created by liujiawei on 18-7-10.
 */
class BusActionBar(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs), View.OnClickListener {

    interface BusActionBarListener {
        fun onClickBarMenu()
        fun onClickBarBack()
        fun onClickBarAdd()
    }

    var listener: BusActionBarListener? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {
        btn_bar_menu.setOnClickListener(this)
        btn_bar_add.setOnClickListener(this)
        btn_bar_back.setOnClickListener(this)
    }

    fun updateActionBarTitle(type: Int = 1) {
        when(type) {
            Constants.ACTION_BAR_MAIN -> {
                tv_bar_title.text = "公交提醒"
                btn_bar_menu.visibility = View.VISIBLE
                btn_bar_back.visibility = View.GONE
                btn_bar_add.visibility = View.VISIBLE
            }
            Constants.ACTION_BAR_ADD_LESTENER -> {
                tv_bar_title.text = "新建提醒"
                btn_bar_menu.visibility = View.GONE
                btn_bar_add.visibility = View.GONE
                btn_bar_back.visibility = View.VISIBLE
            }
            Constants.ACTION_BAR_ADD_DETAIL -> {
                tv_bar_title.text = "线路详情"
                btn_bar_menu.visibility = View.GONE
                btn_bar_add.visibility = View.GONE
                btn_bar_back.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            btn_bar_menu -> listener?.onClickBarMenu()
            btn_bar_add -> listener?.onClickBarAdd()
            btn_bar_back -> listener?.onClickBarBack()
        }
    }

}