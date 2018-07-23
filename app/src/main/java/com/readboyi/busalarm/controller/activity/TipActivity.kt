package com.readboyi.busalarm.controller.activity

import android.app.Service
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.text.TextUtils
import android.view.View
import com.readboyi.busalarm.BusApp
import com.readboyi.busalarm.R
import com.readboyi.busalarm.data.bean.BusListenerBean
import com.readboyi.busalarm.data.database.BusDBManager
import com.readboyi.busalarm.utils.CommonUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tip.*

class TipActivity : AppCompatActivity(), View.OnClickListener {

    private var welcomeMp: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private var mBusDBManager: BusDBManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip)
        init()
    }

    private fun init() {
        mBusDBManager = BusDBManager(this)
        CommonUtils.wakeAndUnlock(this)
        playWelcomeSound()
        setTipText()
        btn_cancel.setOnClickListener(this)
        btn_continue.setOnClickListener(this)
        Picasso.with(this).apply {
            if (!TextUtils.isEmpty(BusApp.INSTANCE.menuImageUrl)) {
                load(BusApp.INSTANCE.menuImageUrl).into(tip_root)
            }
        }
    }

    /**
     * 播放首欢迎语音
     */
    private fun playWelcomeSound() {
        vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator?
        vibrator?.vibrate(longArrayOf(200, 600, 200, 600),0)
        welcomeMp = MediaPlayer.create(baseContext, R.raw.nokia)
        welcomeMp?.isLooping = true
        welcomeMp?.setOnPreparedListener{
            welcomeMp?.start()
        }
    }

    private fun setTipText(){
        tv_tip_key.text = intent.getStringExtra("key")
        tv_tip_station.text = intent.getStringExtra("station")
    }

    override fun onClick(v: View?) {
        when(v) {
            btn_cancel -> {
                mBusDBManager?.updateListenLine(
                        intent.getStringExtra("id"),
                        intent.getStringExtra("station"),
                        1)
                finish()
            }
            btn_continue -> finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vibrator?.cancel()
        welcomeMp?.release()
        welcomeMp = null
    }
}
