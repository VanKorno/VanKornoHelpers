package com.vankorno.vankornohelpers.values

object LibGlobals {
    @Volatile var actRunning = false
    @Volatile var actExists = false
    @Volatile var appStarted = false
    @Volatile var actPaused = false
    
    @Volatile var configChangeJustHappened = false
    @Volatile var updatingScreenNow = false
    
    var screenDensity = 0.0f
    
    @Volatile var androidTestRun = false
    @Volatile var debugBuild = false
    
    val unitTestRun: Boolean by lazy {
        Thread.currentThread().stackTrace.any { it.className.startsWith("org.junit.") }
    }
    
    @Volatile var eLogInUI = false
    
    @Volatile var intForUnitTests = 0
    @Volatile var strForUnitTests = ""
    
    @Volatile var intForAndroidTests = 0
    @Volatile var strForAndroidTests = ""
    @Volatile var firstAndroidTestDone = false
    
    @Volatile var simulatedTime = 0L
    
    
    
    
    
    fun reset() {
        actRunning = false
        actExists = false
        appStarted = false
        actPaused = false
        updatingScreenNow = false
    
        screenDensity = 0.0f
        
        androidTestRun = false
        debugBuild = false
    
        eLogInUI = false
    
        intForUnitTests = 0
        strForUnitTests = ""
    
        intForAndroidTests = 0
        strForAndroidTests = ""
    
        simulatedTime = 0L
        
        
    }
}