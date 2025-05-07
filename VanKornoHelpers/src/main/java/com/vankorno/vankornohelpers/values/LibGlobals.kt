package com.vankorno.vankornohelpers.values

import com.vankorno.vankornohelpers.values.LibConstants.LangAuto

object LibGlobals {
    @Volatile
    var actRunning = false
    @Volatile
    var actExists = false
    @Volatile
    var appStarted = false
    @Volatile
    var actPaused = false
    
    @Volatile
    var updatingScreenNow = false
    
    var screenDensity = 0.0f
    
    var language = LangAuto
    
    
    @Volatile
    var androidTestRun = false
    @Volatile
    var unitTestRun = false
    @Volatile
    var debugBuild = false
    
    @Volatile
    var eLogInUI = false
    
    @Volatile
    var intForUnitTests = 0
    @Volatile
    var strForUnitTests = ""
    
    @Volatile
    var intForAndroidTests = 0
    @Volatile
    var strForAndroidTests = ""
    
    
    
    
}