package com.vankorno.vankornohelpers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object LibClipboard {
    
    private val listeners = mutableSetOf<(String) -> Unit>()
    
    private val _clipboardFlow = MutableStateFlow("")
    val clipboardFlow: StateFlow<String> get() = _clipboardFlow
    
    
    fun addListener(l: (String) -> Unit) {
        listeners += l
    }
    
    fun removeListener(l: (String) -> Unit) {
        listeners -= l
    }
    
    private fun notifyAll(txt: String) {
        _clipboardFlow.value = txt
        listeners.forEach { it(txt) }
    }
    
    
    fun getClipboard(                                                               cont: Context
    ): String {
        val clipboard = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val item = clipboard.primaryClip?.getItemAt(0) ?: return ""
        return item.text.toString()
    }
    
    
    fun setClipboard(                                                               cont: Context,
                                                                                     txt: String,
    ) {
        val clipboard = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("simple text", txt))
        notifyAll(txt)
    }
    
    
    fun clearClipboard(                                                             cont: Context
    ) {
        val clipboardManager = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", ""))
        notifyAll("")
    }
    
    
    
    private var sysListener: ClipboardManager.OnPrimaryClipChangedListener? = null
    
    
    fun attachSystemListener(                                                       cont: Context
    ) {
        if (sysListener != null) return
        
        val clipboardManager = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        
        val listener = ClipboardManager.OnPrimaryClipChangedListener {
            val txt = (clipboardManager.primaryClip?.getItemAt(0)?.text?.toString() ?: "").trim()
            notifyAll(txt)
        }
        sysListener = listener
        clipboardManager.addPrimaryClipChangedListener(listener)
    }
    
    
    fun detachSystemListener(                                                       cont: Context
    ) {
        val listener = sysListener ?: return
        val clipboardManager = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.removePrimaryClipChangedListener(listener)
        sysListener = null
    }
    
}





