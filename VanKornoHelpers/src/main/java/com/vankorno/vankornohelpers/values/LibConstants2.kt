package com.vankorno.vankornohelpers.values

import android.util.Log

val lambdaError: (String)->Unit = { lambdaName ->
    Log.e("Empty lambda error!", "$lambdaName shouldn't be empty at this point, but it is.")
}