package com.vankorno.vankornohelpers.values

import com.vankorno.vankornohelpers.values.ConstGlobals.LangAuto

object AppGlobals {
    @Volatile
    var actRunning: Boolean = false
    
    @Volatile
    var actExists: Boolean = false
    
    @Volatile
    var appStarted: Boolean = false
    
    @Volatile
    var actPaused: Boolean = false
    
    @Volatile
    var updatingScreenNow: Boolean = false
    
    var screenDensity: Float = 0.0f
    
    var language: String = LangAuto
}