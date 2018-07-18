package com.readboyi.busalarm.utils

import java.util.*

/**
 * Created by liujiawei on 18-7-18.
 */
object CommonUtils {

    /**
     * 获取背景图片
     */
    fun getMenuImageUrl (): String {
        val menuImageUrl = "http://p82xye51n.bkt.clouddn.com/Fighting_"
        val suffix = ".png"
        val index = Math.abs(Random().nextInt()) % 202

        return menuImageUrl + index + suffix
    }

}