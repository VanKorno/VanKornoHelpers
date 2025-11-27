package com.vankorno.vankornohelpers.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object LibClipboard {
    
    private val listeners = mutableSetOf<(String) -> Unit>()
    
    private val _clipboardFlow = MutableStateFlow("")
    val clipboardFlow: StateFlow<String> get() = _clipboardFlow
    
    
    fun addListener(l: (String) -> Unit) { listeners += l }
    
    fun removeListener(l: (String) -> Unit) { listeners -= l }
    
    
    private fun notifyAll(                                                           txt: String
    ) {
        _clipboardFlow.value = txt
        listeners.forEach { it(txt) }
    }
    
    
    fun getClipboard(                                                            context: Context
    ): String {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val item = clipboard.primaryClip?.getItemAt(0) ?: return ""
        return item.text.toString()
    }
    
    
    fun setClipboard(                                                            context: Context,
                                                                                     txt: String,
    ) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("simple text", txt))
        notifyAll(txt)
    }
    
    
    fun clearClipboard(                                                          context: Context
    ) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", ""))
        notifyAll("")
    }
    
    
    
    private var sysListener: ClipboardManager.OnPrimaryClipChangedListener? = null
    
    
    fun attachSystemListener(                                                    context: Context
    ) {
        if (sysListener != null) return
        
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        
        val listener = ClipboardManager.OnPrimaryClipChangedListener {
            notifyAll(getClipboard(context))
        }
        sysListener = listener
        clipboardManager.addPrimaryClipChangedListener(listener)
    }
    
    
    fun detachSystemListener(                                                    context: Context
    ) {
        val listener = sysListener ?: return
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.removePrimaryClipChangedListener(listener)
        sysListener = null
    }
    
    
    
    
}