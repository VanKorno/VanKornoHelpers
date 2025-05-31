package com.vankorno.vankornohelpers.util

import com.vankorno.vankornohelpers.eLog
import kotlinx.coroutines.*

object LibCoroutines {
    val IO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val Default = CoroutineScope(Dispatchers.Default + SupervisorJob())
    val Main = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    
    /** A special handler that catches uncaught exceptions in my coroutines **/
    val logHandler = CoroutineExceptionHandler { _, t ->
        eLog("AppCoroutines", "Unhandled: ${t.message}", t)
    }
    
    
    /**
     * A helper function to run a group of coroutine tasks where if one fails,
     * it won't cancel the others automatically, but still lets me catch errors cleanly.
     */
    suspend fun <T> runSafe(                                    block: suspend CoroutineScope.()->T
    ): T = supervisorScope {
        try {
            block()
        } catch (e: Exception) {
            eLog("AppCoroutines", "runSafe error: ${e.message}", e)
            throw e
        }
    }
    
    
    fun <T> blockingIO(                                                     block: suspend () -> T
    ): T = runBlocking {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}