package com.vankorno.vankornohelpers

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.vankorno.vankornohelpers.sql.LibConstantsDB.InMemoryDB
import com.vankorno.vankornohelpers.values.LibGlobals.instrTestRun

class LibMisc {
    
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
    
    
    fun isInstrumentedTestRun(): Boolean = try {
        Class.forName("androidx.test.platform.app.InstrumentationRegistry")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
    
    fun getDbName(fileName: String) = if (instrTestRun)  InMemoryDB  else  fileName
    
    
    
}