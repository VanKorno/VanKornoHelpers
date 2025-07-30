package com.vankorno.vankornohelpers

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

data class SizeWH(val w: Int, val h: Int)

fun getRealScreenSizePx(                                                         context: Context
): SizeWH {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val wm = context.getSystemService(WindowManager::class.java)
        val bounds = wm.currentWindowMetrics.bounds
        SizeWH(bounds.width(), bounds.height())
    } else {
        val displayMetrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getRealMetrics(displayMetrics)
        SizeWH(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}










