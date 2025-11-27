package com.vankorno.vankornohelpers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


class LibClipBoard {
    
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
    }
    
    
    fun clearClipboard(                                                             cont: Context
    ) {
        val clipboardManager = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val emptyClip = ClipData.newPlainText("", "")
        clipboardManager.setPrimaryClip(emptyClip)
    }
    
    
}