package com.readboyi.busalarm.wedget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * Created by liujiawei on 18-7-10.
 */
class MenuRootView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), Target {

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {}
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        setBackgroundDrawable(BitmapDrawable(resources, bitmap))
    }

}