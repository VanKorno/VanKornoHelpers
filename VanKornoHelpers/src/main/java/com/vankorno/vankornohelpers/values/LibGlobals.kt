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
    
    @Volatile
    var simulatedTime = 0L
    
    
    fun reset() {
        actRunning = false
        actExists = false
        appStarted = false
        actPaused = false
        updatingScreenNow = false
    
        screenDensity = 0.0f
        language = LangAuto
    
        androidTestRun = false
        unitTestRun = false
        debugBuild = false
    
        eLogInUI = false
    
        intForUnitTests = 0
        strForUnitTests = ""
    
        intForAndroidTests = 0
        strForAndroidTests = ""
    
        simulatedTime = 0L
    }
}