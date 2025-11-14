package com.vankorno.vankornohelpers

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


class LibClipBoard {
    
    fun getTxt(                                                                  cont: Context
    ): String {
        val clipboard = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val item = clipboard.primaryClip?.getItemAt(0) ?: return ""
        return item.text.toString()
    }
    
    
    fun setTxt(                                                                  cont: Context,
                                                                                  txt: String,
    ) {
        val clipboard = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("simple text", txt))
    }
    
    
    fun clearBuffer(                                                             cont: Context
    ) {
        val clipboardManager = cont.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val emptyClip = ClipData.newPlainText("", "")
        clipboardManager.setPrimaryClip(emptyClip)
    }
    
    
}