package com.readboyi.busalarm

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.view.GravityCompat
import android.view.View
import com.github.mzule.fantasyslide.SimpleFantasyListener
import com.pgyersdk.crash.PgyCrashManager
import com.readboyi.busalarm.controller.fragment.BusListenerListFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import com.readboyi.busalarm.config.Constants
import com.readboyi.busalarm.controller.activity.SecondActivity
import com.readboyi.busalarm.wedget.BusActionBar
import kotlinx.android.synthetic.main.view_action_bar.*

class MainActivity : AppCompatActivity(), BusActionBar.BusActionBarListener {

    var tmp: TextView? = null

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

    override fun onClickBarMenu() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onClickBarAdd() {
        toSecondActivity(Constants.ACTION_BAR_ADD_LESTENER)
    }

    override fun onClickBarBack() {}

    private fun initMenu() {
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        leftSideBar.setFantasyListener(object : SimpleFantasyListener() {

            override fun onHover(@Nullable view: View?): Boolean {
                if (view is TextView) {
                    tmp?.textSize = 18F
                    tmp = view
                    view.textSize = 22F
                }
                return false
            }

            override fun onSelect(view: View): Boolean {
                tmp?.textSize = 18F
                when (view) {
                    tv_new_listener -> {
                        toSecondActivity(Constants.ACTION_BAR_ADD_LESTENER)
                    }
                }
                return false
            }

            override fun onCancel() {
                tmp?.textSize = 18F
            }

        })
    }

    /**
     * 跳转到第二个页面
     */
    private fun toSecondActivity(type: Int = 1){
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
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
