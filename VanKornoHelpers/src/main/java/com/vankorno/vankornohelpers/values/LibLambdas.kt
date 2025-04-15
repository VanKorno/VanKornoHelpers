package com.vankorno.vankornohelpers.values

import com.vankorno.vankornohelpers.lambdaError


var minimizeApp: ()->Unit = { lambdaError("minimizeApp") }

var longToast: (String)->Unit = {}
var shortToast: (String)->Unit = {}

var playSound: (Int)->Unit = { lambdaError("playSound") }

var showKeyboard: ()->Unit = {}
var hideKeyboard: ()->Unit = {}


var getBuffer: ()->String = {
    lambdaError("getBuffer") 
    ""
}
var setBuffer: (String)->Unit = { lambdaError("setBuffer") }






