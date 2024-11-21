package com.vankorno.vankornohelpers

import android.app.Activity
import android.graphics.drawable.ColorDrawable

class LibUI {
    
    fun setWindowBackgroundColor(                                        activity: Activity,
                                                                            color: Int = -0xe4e4e5
    ) {
        val myDrawable = ColorDrawable(color)
        activity.window.setBackgroundDrawable(myDrawable)
    }
    
    
    
    fun getCircleIcon(isON: Boolean) =  if (isON)
                                            R.drawable.ic_check_circle_filled
                                        else
                                            R.drawable.ic_check_circle_empty
    
    fun getCheckBoxIcon(isON: Boolean) =  if (isON)
                                            R.drawable.ic_check_box_filled
                                        else
                                            R.drawable.ic_check_box_empty
    
    
}