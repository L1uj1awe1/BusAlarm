package com.readboyi.busalarm

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
import android.widget.Toast


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        PgyCrashManager.register(this)
        initView()
        initMenu()
        initFragment()
    }

    private fun initView(){
        btn_menu.setOnClickListener(this)
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
                tipView.visibility = View.VISIBLE
                if (view is TextView) {
                    tipView.text = view.text.toString()
                }
                return false
            }

            override fun onSelect(view: View): Boolean {
                tipView.visibility = View.INVISIBLE
                if (view is TextView) {
                    Toast.makeText(applicationContext, view.text.toString(), Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onCancel() {
                tipView.visibility = View.INVISIBLE
            }

        })
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
