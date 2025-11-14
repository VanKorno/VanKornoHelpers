package com.vankorno.vankornohelpers

import android.util.Log
import com.vankorno.vankornohelpers.values.LibGlobals.debugBuild
import com.vankorno.vankornohelpers.values.LibGlobals.eLogInUI
import com.vankorno.vankornohelpers.values.LibGlobals.unitTestRun
import com.vankorno.vankornohelpers.values.longToast

private const val MaxToastLength = 200

fun dLog(                                                                            tag: String,
                                                                                     msg: String,
) {
    if (!debugBuild)  return  //\/\/\/\/\/\
    
    if (unitTestRun)
        println("Dbg ($tag): $msg")
    else
        Log.d(tag, msg)
}

fun wLog(                                                                            tag: String,
                                                                                     msg: String,
) {
    if (!debugBuild)  return  //\/\/\/\/\/\
    
    if (unitTestRun)
        println("Warning ($tag): $msg")
    else
        Log.w(tag, msg)
}


fun eLog(                                                                    tag: String,
                                                                             msg: String,
                                                                       throwable: Throwable? = null,
) {
    if (unitTestRun) {
        println("ERROR! ($tag): $msg")
        throwable?.printStackTrace()
    } else {
        Log.e(tag, msg, throwable)
        if (debugBuild  &&  eLogInUI) {
            withUI {
                val errMsg =    if (msg.length > MaxToastLength)
                                    msg.take(MaxToastLength) + "…"
                                else
                                    msg
                
                longToast("Error ($tag): $errMsg")
            }
        }
    }
}


fun lambdaError(lambdaName: String) = eLog("Empty lambda!", "$lambdaName should not be empty at this point, but it is.")








