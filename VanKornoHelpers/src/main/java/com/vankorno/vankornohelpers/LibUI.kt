package com.vankorno.vankornohelpers

import android.app.Activity
import androidx.core.graphics.drawable.toDrawable

class LibUI {
    
    fun setWindowBackgroundColor(                                        activity: Activity,
                                                                            color: Int = -0xe4e4e5,
    ) {
        val myDrawable = color.toDrawable()
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
