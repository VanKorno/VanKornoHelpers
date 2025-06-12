package com.vankorno.vankornohelpers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class LibPic(                                                        private val context: Context
) {
    private val imagesDir = File(context.filesDir, "user_pics").apply { mkdirs() }
    
    
    fun saveImageFromUri(                                                      uri: Uri,
                                                                        namePrefix: String = "pic"
    ): String {
        val filename = "${namePrefix}_${System.currentTimeMillis()}.png"
        val outFile = File(imagesDir, filename)
        
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(outFile).use { output ->
                input.copyTo(output)
            }
        }
        return "user_pics/$filename" // Return relative path for DB
    }
    
    
    fun deleteImage(                                                                path: String
    ): Boolean {
        val file = File(context.filesDir, path)
        return file.exists() && file.delete()
    }
    
    
    fun getFile(path: String): File = File(context.filesDir, path)
    
    fun getUri(path: String): Uri = getFile(path).toUri()
    
    
    fun listImageFiles(): List<File> = imagesDir.listFiles()?.toList().orEmpty()
    
    
    fun clearAllImages(): Int = imagesDir.listFiles()?.count { it.delete() } ?: 0
    
    
    fun exists(path: String): Boolean = getFile(path).exists()
    
    
    fun getImageSize(                                                               path: String
    ): Pair<Int, Int>? {
        return try {
            val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeFile(getFile(path).absolutePath, opts)
            opts.outWidth to opts.outHeight
        } catch (e: Exception) {
            null
        }
    }
    
    
    fun updateImageFromUri(                                                          uri: Uri,
                                                                                filename: String
    ): Boolean {
        val file = File(imagesDir, filename)
        return try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }
    
    
    
    
}