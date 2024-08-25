package com.vankorno.vankornohelpers

import android.content.Context
import android.media.SoundPool
import android.util.Log

class SoundPoolHelpr(                                              private val context: Context,
                                                                                sounds: Array<Int>
) {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(10)
        .build()
    
    private val soundMap: MutableMap<Int, Int> = mutableMapOf()
    
    
    init { // Preload sounds into the SoundPool
        sounds.forEach { sound ->
            soundMap[sound] = soundPool.load(context, sound, 1)
        }
    }
    
    
    fun playSound(                                                                 sound: Int
    ) {
        val soundId = soundMap[sound]
        
        if (soundId != null) { // Play the sound if it's already loaded
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
        }
        else { // Load the sound if it's not preloaded
            // region LOG
            Log.w("SoundPoolHelpr", "The sound isn't preloaded. Loading it now.")
            // endregion
            val loadId = soundPool.load(context, sound, 1)
            
            // Use a listener to play the sound once it's loaded
            soundPool.setOnLoadCompleteListener { _, sampleId, _ ->
                if (sampleId == loadId) {
                    soundMap[sound] = loadId // Cache the loaded sound
                    soundPool.play(loadId, 1f, 1f, 1, 0, 1f)
                }
            }
        }
    }
    
    
    fun release() {
        soundPool.release()
    }
}