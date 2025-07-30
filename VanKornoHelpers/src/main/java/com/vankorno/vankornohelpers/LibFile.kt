package com.vankorno.vankornohelpers

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import java.io.File

/** 
 * Returns the MIME type for the given file path based on its extension.
 * Useful for sharing, uploading, or identifying the content type of a file.
 */
fun getMimeType(                                                                    path: String
): String? = MimeTypeMap
                .getSingleton()
                .getMimeTypeFromExtension(File(path).extension.lowercase())



fun fileExists(file: File): Boolean = file.exists()

fun fileExists(context: Context, path: String): Boolean = getFileInAppStorage(context, path).exists()

fun getFileInAppStorage(context: Context, path: String): File = File(context.filesDir, path)

fun getUriForAppFile(context: Context, path: String): Uri = getFileInAppStorage(context, path).toUri()




