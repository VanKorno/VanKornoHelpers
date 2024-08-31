package com.vankorno.vankornohelpers

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class MiscHelpr {
    companion object {
        const val iOFF = -1
    }
    
    fun getTime24(h: Int, m: Int): String = ""+h+":"+addZero(m)
    
    private fun addZero(num: Int): String = if (num < 10)  "0"+num  else  ""+num
    
    
    fun makeToast(                                                               context: Context,
                                                                                     txt: String,
                                                                                  length: Int,
                                                                              activityON: Boolean
    ) {
        if (!activityON  ||  txt.isBlank())  return  //\/\/\/\/\/\
        
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, txt, length).show()
        }
    }
    
    
    fun setWindowBackgroundColor(                                        activity: Activity,
                                                                            color: Int = -0xe4e4e5
    ) {
        val myDrawable = ColorDrawable(color)
        activity.window.setBackgroundDrawable(myDrawable)
    }
    
    
    
    
    
    
    
}