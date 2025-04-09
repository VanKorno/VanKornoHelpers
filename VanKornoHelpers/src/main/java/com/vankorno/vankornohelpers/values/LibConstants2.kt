package com.vankorno.vankornohelpers.values

import android.util.Log
import com.vankorno.vankornohelpers.values.LibGlobals.unitTestRun

val lambdaError: (String)->Unit = { lambdaName ->
    if (unitTestRun)
        println("lambdaError: $lambdaName")
    else
        Log.e("Empty lambda error!", "$lambdaName shouldn't be empty at this point, but it is.")
}