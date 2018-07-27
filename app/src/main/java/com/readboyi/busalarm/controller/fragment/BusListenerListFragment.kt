package com.readboyi.busalarm.controller.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readboyi.busalarm.R
import com.readboyi.busalarm.adapter.BusListenerAdapter
import com.readboyi.busalarm.data.bean.BusListenerBean
import com.readboyi.busalarm.data.database.BusDBManager
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.fragment_bus_listener.*
import java.util.ArrayList

class BusListenerListFragment : Fragment() {

    private var showSwiperMenu: Boolean = false
    private var mBusDBManager: BusDBManager? = null
    private var mAdapter: BusListenerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bus_listener, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        notifyDataChange()
    }

    private fun init() {
        mBusDBManager = BusDBManager(context)
        initRecyclerView()
    }

    private fun initRecyclerView () {
        mAdapter = BusListenerAdapter()
        list_bus_listener.run {
            if (showSwiperMenu) {
                setSwipeMenuCreator({ swipeLeftMenu, swipeRightMenu, viewType ->
                    val width = resources.getDimensionPixelSize(R.dimen.dp_100)
                    val height = resources.getDimensionPixelSize(R.dimen.dp_80)
                    val deleteItem = SwipeMenuItem(context)
                            .setBackground(R.drawable.item_menu_bg)
                            .setText("删除")
                            .setTextColor(Color.WHITE)
                            .setTextSize(16)
                            .setWidth(width)
                            .setHeight(height)
                    swipeRightMenu.addMenuItem(deleteItem)// 添加菜单到右侧。
                })
                setSwipeMenuItemClickListener({ menuBridge ->
                    menuBridge.closeMenu()
                    val adapterPosition = menuBridge.adapterPosition
                    val menuPosition = menuBridge.position
                    if(menuPosition == 0){
                        Log.e("jiajia","策划菜单 - 删除")
                    }
                })
            } else {
                isLongPressDragEnabled = true
//                isItemViewSwipeEnabled = true
                setOnItemMoveListener(object : OnItemMoveListener{

                    override fun onItemMove(srcHolder: RecyclerView.ViewHolder?, targetHolder: RecyclerView.ViewHolder?): Boolean {
                        return false
                    }

                    override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) {
                        mAdapter?.swipeDeleteItem(srcHolder?.adapterPosition ?: -1)
                    }

                })
            }
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
    }

    fun notifyDataChange (){
        val list: ArrayList<BusListenerBean> = mBusDBManager?.queryListenStations() ?: ArrayList<BusListenerBean>()
        (mAdapter as BusListenerAdapter).list = list
        (mAdapter as BusListenerAdapter).mBusDBManager = mBusDBManager
        mAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBusDBManager?.close()
    }

    companion object {

        fun newInstance(): BusListenerListFragment {
            val fragment = BusListenerListFragment()
            return fragment
        }
    }
}
