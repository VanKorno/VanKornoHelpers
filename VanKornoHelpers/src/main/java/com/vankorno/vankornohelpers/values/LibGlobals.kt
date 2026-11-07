package com.vankorno.vankornohelpers.values

import com.vankorno.vankornohelpers.LibMisc

object LibGlobals {
    @Volatile var actRunning = false
    @Volatile var actExists = false
    @Volatile var appStarted = false
    @Volatile var actPaused = false
    
    @Volatile var configChangeJustHappened = false
    @Volatile var updatingScreen = false
    @Volatile var navigating = false
    @Volatile var navigatingBack = false
    
    var screenDensity = 0.0f
    
    @Volatile var debugBuild = false
    
    @Volatile
    var androidTestsRun: Boolean = LibMisc().isInstrumentedTestRun()
    
    val testsRun: Boolean by lazy {
        androidTestsRun || try { // Checks if JUnit is present on the runtime classpath, regardless of which thread calls it
            Class.forName("org.junit.Test")
            true
        } catch (_: ClassNotFoundException) {
            false
        }
    }
    val unitTestsRun: Boolean
        get() = testsRun && !androidTestsRun
    
    
    @Volatile var eLogInUI = false
    
    @Volatile var intForUnitTests = 0
    @Volatile var strForUnitTests = ""
    
    @Volatile var intForAndroidTests = 0
    @Volatile var strForAndroidTests = ""
    @Volatile var firstAndroidTestDone = false
    
    @Volatile var simulatedTime = 0L
    
    const val UserMe = 1
    const val UserTester = 2
    const val UserPublic = 3
    @Volatile var appUser = UserPublic
        set(value) {
            field = value
            eLogInUI = value == UserMe || value == UserTester
        }
    
    
    
    fun reset() {
        actRunning = false
        actExists = false
        appStarted = false
        actPaused = false
        updatingScreen = false
        navigating = false
        navigatingBack = false
    
        screenDensity = 0.0f
        
        androidTestsRun = false
        debugBuild = false
    
        eLogInUI = false
    
        intForUnitTests = 0
        strForUnitTests = ""
    
        intForAndroidTests = 0
        strForAndroidTests = ""
    
        simulatedTime = 0L
        
        
    }
}
