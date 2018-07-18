package com.readboyi.busalarm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.controller.fragment.BusListenerListFragment
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.controller.activity.SecondActivity
import com.readboyi.busalarm.wedget.BusActionBar
import kotlinx.android.synthetic.main.view_action_bar.*
import com.jpeng.jpspringmenu.SpringMenu
import android.view.MotionEvent
import com.facebook.rebound.SpringConfig
import com.jpeng.jpspringmenu.MenuListener
import kotlinx.android.synthetic.main.menu.*

class MainActivity : AppCompatActivity(), BusActionBar.BusActionBarListener {

    var busListenerFragment: BusListenerListFragment? = null
    var menu: SpringMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        PgyCrashManager.register(this)
        initMenu()
        initFragment()
        initView()
    }

    private fun initView() {
        bus_action_bar.listener = this
        bus_action_bar.updateActionBarTitle(Constants.ACTION_BAR_MAIN)
    }

    private fun initMenu() {
        menu = SpringMenu(this, R.layout.menu)
        menu?.setMenuWidth((300 * BusApp.INSTANCE.density).toInt())
        menu?.setMenuSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20.0, 3.0))
        menu?.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20.0, 5.0))
        menu?.setFadeEnable(false)
        menu?.setDragOffset(0.4f)
        menu?.setDirection(0)
        menu?.setMenuListener(object : MenuListener{
            override fun onMenuClose() {
                btn_bar_menu.setBackgroundResource(R.drawable.menu)
            }

            override fun onProgressUpdate(value: Float, bouncing: Boolean) {}

            override fun onMenuOpen() {
                btn_bar_menu.setBackgroundResource(R.drawable.back)
            }

        })
        tv_new_listener.setOnClickListener { toSecondActivity(Constants.ACTION_BAR_ADD_LESTENER) }
    }

    override fun onClickBarMenu() {
        if (menu?.isOpened ?: true) {
            menu?.closeMenu()
        } else {
            menu?.openMenu()
        }
    }

    override fun onClickBarAdd() {
        toSecondActivity(Constants.ACTION_BAR_ADD_LESTENER)
    }

    override fun onClickBarBack() {}

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return menu?.dispatchTouchEvent(ev) ?: false
    }

    /**
     * 跳转到第二个页面
     */
    private fun toSecondActivity(type: Int = 1){
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
        if (menu?.isOpened ?: false) {
            menu?.closeMenu()
        }
    }

    private fun initFragment() {
        busListenerFragment = BusListenerListFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fl_bus_listener_layout, busListenerFragment).commit()
    }

    override fun onResume() {
        super.onResume()
        busListenerFragment?.notifyDataChange()
    }

    override fun onDestroy() {
        super.onDestroy()
        PgyCrashManager.unregister()
    }
}
