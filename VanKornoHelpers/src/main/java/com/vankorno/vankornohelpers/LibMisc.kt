package com.vankorno.vankornohelpers

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.vankorno.vankornohelpers.values.LibGlobals.actExists
import com.vankorno.vankornohelpers.values.LibGlobals.actRunning
import com.vankorno.vankornohelpers.values.LibGlobals.androidTestRun
import com.vankorno.vankornohelpers.values.LibGlobals.appStarted

class LibMisc {
    
    fun getTime24(h: Int, m: Int): String = ""+h+":"+addZero(m)
    
    private fun addZero(num: Int): String = if (num < 10)  "0"+num  else  ""+num
    
    
    fun makeToast(                                                               context: Context,
                                                                                     txt: String,
                                                                                  length: Int
    ) {
        if (txt.isBlank())  return  //\/\/\/\/\/\
        
        withUI {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, txt, length).show()
            }
        }
    }
    
    
    fun isInstrumentedTestRun(): Boolean = try {
        Class.forName("androidx.test.platform.app.InstrumentationRegistry")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
    
    fun getDbName(fileName: String) = if (androidTestRun)  ":memory:"  else  fileName
    
}


/**
 * Run stuff when UI is fully operational, after the start of the app.
 */
inline fun withUI(                                                                   run: ()->Unit
) {
    if (appStarted && actExists && actRunning) {
        run()
    }
}





