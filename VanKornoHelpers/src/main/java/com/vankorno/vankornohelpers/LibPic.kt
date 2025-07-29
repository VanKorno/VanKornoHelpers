package com.vankorno.vankornohelpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

private const val TAG = "LibPic"
private const val PicPrefix = "pic"
const val PicInternalFolder = "user_pics"

class LibPic(                                                        private val context: Context
) {
    private val imagesDir = File(context.filesDir, PicInternalFolder).apply { mkdirs() }
    
    
    fun saveImageFromUri(                                                   uri: Uri,
                                                                     namePrefix: String = PicPrefix
    ): String {
        // region LOG
            dLog(TAG, "saveImageFromUri()")
        // endregion
        val filename = "${namePrefix}_${System.currentTimeMillis()}.png"
        val outFile = File(imagesDir, filename)
        
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(outFile).use { output ->
                input.copyTo(output)
            }
        }
        return "$PicInternalFolder/$filename" // Return relative path for DB
    }
    
    
    fun deleteImage(                                                                path: String
    ): Boolean {
        // region LOG
            dLog(TAG, "deleteImage(path = $path)")
        // endregion
        if (path.isBlank()) return false //\/\/\/\/\/\
        
        val file = File(context.filesDir, path)
        return file.exists() && file.delete()
    }
    
    
    fun getFile(path: String): File = File(context.filesDir, path)
    
    fun getUri(path: String): Uri = getFile(path).toUri()
    
    
    fun listImageFiles(): List<File> = imagesDir.listFiles()?.toList().orEmpty()
    
    
    fun clearAllImages(): Int {
        // region LOG
            dLog(TAG, "clearAllImages()")
        // endregion
        return imagesDir.listFiles()?.count { it.delete() } ?: 0
    }
    
    
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
        // region LOG
            dLog(TAG, "updateImageFromUri(filename = $filename)")
        // endregion
        val file = File(imagesDir, filename)
        return try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            eLog(TAG, "updateImageFromUri(): Update failed!", e)
            false
        }
    }
    
    
    fun getBitmapFromPath(path: String) = BitmapFactory.decodeFile(getFile(path).absolutePath)
    
    
    fun saveBitmapAsNewFile(              bitmap: Bitmap,
                                      namePrefix: String = PicPrefix,
                                          format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                                         quality: Int = 100
    ): String {
        // region LOG
            dLog(TAG, "saveBitmapAsNewFile(format = ${format.name}, quality = $quality)")
        // endregion
        val extension = when (format) {
            Bitmap.CompressFormat.PNG -> "png"
            Bitmap.CompressFormat.JPEG -> "jpg"
            Bitmap.CompressFormat.WEBP -> "webp"
            else -> "img"
        }
        val filename = "${namePrefix}_${System.currentTimeMillis()}.$extension"
            
        saveBitmapAt(filename, bitmap, format, quality)
            
        return "$PicInternalFolder/$filename"
    }
    
    
    fun saveBitmapAt(                       path: String,
                                          bitmap: Bitmap,
                                          format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                                         quality: Int = 100
    ): Boolean {
        // region LOG
            dLog(TAG, "saveBitmapAsNewFile(path = $path, format = ${format.name}, quality = $quality)")
        // endregion
        if (path.isBlank()) {
            // region LOG
                eLog(TAG, "saveBitmapAt() Failed because path is blank or empty.")
            // endregion
            return false //\/\/\/\/\/\
        }
        
        return try {
            val file = getFile(path)
            FileOutputStream(file).use { out ->
                bitmap.compress(format, quality, out)
            }
            true
        } catch (e: Exception) {
            // region LOG
                eLog(TAG, "saveBitmapAt() Failed!", e)
            // endregion
            false
        }
    }
    
    
}






