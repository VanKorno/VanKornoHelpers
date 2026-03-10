package com.vankorno.vankornohelpers.fileOps

import android.content.Context
import java.io.File

class AppFiles(                                                      private val context: Context
) {
    fun dir(                                                                 name: String,
                                                                            block: FileOps.()->Unit,
    ) {
        val folder = File(context.filesDir, name).apply { mkdirs() }
        FileOps(context, name, folder).block()
    }
}