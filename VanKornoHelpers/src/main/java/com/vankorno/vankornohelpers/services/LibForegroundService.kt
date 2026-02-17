package com.vankorno.vankornohelpers.services

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import androidx.core.app.NotificationCompat
import com.vankorno.vankornohelpers.dLog
import com.vankorno.vankornohelpers.eLog

private const val TAG = "ForegroundServ."

open class LibForegroundService (                            val activityClass: Class<out Activity>,
                                                                     val title: String,
                                                               val channelName: String,
                                                                      val icon: Int,
                                                              val foregroundID: Int,
                                                                   val notifID: String,
                                                               val wakeLockTag: String,
                                                                val contentTxt: String = "",
                                                             val wakeLockHours: Long = 12,
): Service() {
    
    override fun onBind(intent: Intent): IBinder? = null
    
    lateinit var wakeLock: WakeLock
    
    protected open fun doOnStart(intent: Intent) {}
    
    override fun onStartCommand(                                                  intent: Intent?,
                                                                                   flags: Int,
                                                                                 startId: Int,
    ): Int {
        // region LOG
            dLog(TAG, "onStartCommand()")
        // endregion
        if (intent == null) {
            // region LOG
                eLog(TAG, "onStartCommand() error: intent is null")
            // endregion
            return START_NOT_STICKY
        }
        doOnStart(intent)
        return START_STICKY
    }
    
    
    fun makeForeground() {
        // region LOG
            dLog(TAG, "makeForeground()")
        // endregion
        val notif = createNotification(
            channelName = channelName,
            title = title,
            contentTxt = contentTxt,
            icon = icon
        )
        startForeground(foregroundID, notif)
        
        wakeLock = makeWakeLock(wakeLockTag)
        wakeLock.acquire(wakeLockHours*60*60*1000L)
    }
    
    
    private fun createNotification(                                          channelName: String,
                                                                                   title: String,
                                                                              contentTxt: String,
                                                                                    icon: Int,
    ): Notification {
        createNotifChannel(channelName)
        
        val notifIntent = Intent(this, activityClass)
        val pendingIntent = PendingIntent.getActivity(this, 0, notifIntent,  PendingIntent.FLAG_IMMUTABLE)
        
        return NotificationCompat.Builder(this, notifID)
                .setContentTitle(title)
                .setContentText(contentTxt)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .build()
    }
    
    private fun createNotifChannel(                                          channelName: String
    ) {
        val notifChannel = NotificationChannel(notifID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        val manager = this.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(notifChannel)
    }
    
    
    private fun makeWakeLock(                                                wakeLockTag: String
    ): WakeLock {
        val powerManager = this.getSystemService(POWER_SERVICE) as PowerManager
        return powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, wakeLockTag)
    }
    
    
    
    
    protected open fun beforeOnDestroy() {}
    
    override fun onDestroy() {
        // region LOG
            dLog(TAG, "onDestroy()")
        // endregion
        beforeOnDestroy()
        
        stopFgService()
        super.onDestroy()
    }
    
    fun stopFgService() {
        // region LOG
            dLog(TAG, "stopFGService()")
        // endregion
        if (::wakeLock.isInitialized && wakeLock.isHeld)
            wakeLock.release()
        
        stopForeground(STOP_FOREGROUND_REMOVE)
    }
    
}
