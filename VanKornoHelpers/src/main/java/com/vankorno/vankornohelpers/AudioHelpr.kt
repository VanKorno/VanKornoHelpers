package com.vankorno.vankornohelpers

import android.content.Context
import android.media.MediaPlayer

class AudioHelpr {
    
    private var player: MediaPlayer? = null
    
    fun playerStart(                                                             context: Context?,
                                                                                   sound: Int
    ) {
        if (context == null)  return  //\/\/\/\/\/\
        
        if (player == null) {
            player = MediaPlayer.create(context.applicationContext, sound)
            player?.setOnCompletionListener { playerStop() }
        }
        player?.start()
    }
    
    private fun playerStop() {
        player?.apply{
            setOnCompletionListener(null)
            stop()
            reset()
            release()
        }
        player = null
    }
    
}