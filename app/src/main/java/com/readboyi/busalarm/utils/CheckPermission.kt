package com.readboyi.busalarm.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

object CheckPermission{

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    /**
     * 请求码
     */
    private val REQUEST_CODE_EXTERNAL = 0

    /**
     * 判断是否有权限没有授权
     */
    fun checkPermission(context: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.indices
                    .filter { ActivityCompat.checkSelfPermission(context, permissions[it]) != PackageManager.PERMISSION_GRANTED }
                    .forEach { ActivityCompat.requestPermissions(context, permissions, code(permissions[it])) }
        }
    }

    /**
     * 根据权限名获取请求码
     */
    private fun code(permission: String): Int {
        return when(permission){
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> REQUEST_CODE_EXTERNAL
            else -> -1
        }
    }
}
