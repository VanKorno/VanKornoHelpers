package com.vankorno.vankornohelpers.values

import com.vankorno.vankornohelpers.lambdaError


var minimizeApp: ()->Unit = { lambdaError("minimizeApp") }

var longToast: (String)->Unit = {}
var shortToast: (String)->Unit = {}

var playSound: (Int)->Unit = { lambdaError("playSound") }

var showKeyboard: ()->Unit = {}
var hideKeyboard: ()->Unit = {}
//var reqFocusInput: ()->Unit = {}
var clearFocus: ()->Unit = { lambdaError("clearFocus") }


var getClipboard: ()->String = {
    lambdaError("getClipboard") 
    ""
}
var setClipboard: (String)->Unit = { lambdaError("setClipboard") }






