package com.vankorno.vankornohelpers.values

import com.vankorno.vankornohelpers.values.LibConstants.LangAuto

object LibGlobals {
    @Volatile
    var instrTestRun = false
    
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
    
}