package com.vankorno.vankornohelpers

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager

data class SizeWH(val w: Int, val h: Int)


fun getScreenRatioFloat(                                                         context: Context
): Float {
    val sizeWH = getRealScreenSizePx(context)
    return sizeWH.w.toFloat() / sizeWH.h.toFloat()
}


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


fun getUsableScreenSizePx(                                                       context: Context
): SizeWH {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val wm = context.getSystemService(WindowManager::class.java)
        val windowMetrics = wm.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars()
            )
        val bounds = windowMetrics.bounds
        val width = bounds.width() - insets.left - insets.right
        val height = bounds.height() - insets.top - insets.bottom
        SizeWH(width, height)
    }
    else {
        val displayMetrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getMetrics(displayMetrics)
        SizeWH(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}







