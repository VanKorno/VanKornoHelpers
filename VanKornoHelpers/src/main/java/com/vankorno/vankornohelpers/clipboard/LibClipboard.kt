package com.vankorno.vankornohelpers.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object LibClipboard {
    
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
    }
    
    
    fun clearClipboard(                                                          context: Context
    ) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", ""))
    }
    
    
    
    
    
}