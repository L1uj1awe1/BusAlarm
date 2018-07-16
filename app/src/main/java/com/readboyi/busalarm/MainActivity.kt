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
import com.readboyi.busalarm.controller.activity.SecondActivity
import kotlinx.android.synthetic.main.view_action_bar.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

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
        btn_menu.setOnClickListener(this)
        btn_add.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            btn_menu -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }
    }

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
                        toSecondActivity()
                    }
                }
                return false
            }

            override fun onCancel() {
                tmp?.textSize = 18F
            }

        })
    }

    private fun toSecondActivity(){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
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
