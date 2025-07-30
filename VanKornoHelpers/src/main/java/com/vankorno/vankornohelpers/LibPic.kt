package com.vankorno.vankornohelpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.graphics.scale
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

private const val TAG = "LibPic"

class LibPic(             private val context: Context,
                          private val nameGen: ()->String = { "pic_${System.currentTimeMillis()}" },
                    private val picFolderName: String = "user_pics"
) {
    private val imagesDir = File(context.filesDir, picFolderName).apply { mkdirs() }
    
    
    fun getAbsolutePath(path: String): String = getFile(path).absolutePath
    
    
    fun getMimeType(path: String): String? =
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(File(path).extension)
    
    
    fun saveImageFromUri(                                                      uri: Uri,
                                                                         extension: String = "png"
    ): String {
        // region LOG
            dLog(TAG, "saveImageFromUri()")
        // endregion
        val filename = "${nameGen()}.$extension"
        val outFile = File(imagesDir, filename)
        
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(outFile).use { output ->
                input.copyTo(output)
            }
        }
        return "$picFolderName/$filename" // Return relative path for DB
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
            // region LOG
                eLog(TAG, "getImageSize() failed!", e)
            // endregion
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
            // region LOG
                eLog(TAG, "updateImageFromUri(): Update failed!", e)
            // endregion
            false
        }
    }
    
    
    fun getBitmapFromPath(path: String) = BitmapFactory.decodeFile(getFile(path).absolutePath)
    
    
    fun saveBitmapAsNewFile(              bitmap: Bitmap,
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
        val filename = "${nameGen()}.$extension"
            
        saveBitmapAt(filename, bitmap, format, quality)
            
        return "$picFolderName/$filename"
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
    
    
    fun resizeImageToFitScreen(             path: String,
                                          format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                                         quality: Int = 100
    ) = resizeImageToScreenFraction(path, 1f, format, quality)
    
    
    
    /** fraction: 1 = 100% */
    fun resizeImageToScreenFraction(        path: String,
                                        fraction: Float,
                                          format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                                         quality: Int = 100
    ): Boolean {
        val screen = getRealScreenSizePx(context)
        val maxW = (screen.w * fraction).toInt()
        val maxH = (screen.h * fraction).toInt()
        
        return resizeImagePreserveAspect(
            path = path,
            maxWidth = maxW,
            maxHeight = maxH,
            format = format,
            quality = quality
        )
    }
    
    
    fun resizeImagePreserveAspect(          path: String,
                                        maxWidth: Int,
                                       maxHeight: Int,
                                          format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                                         quality: Int = 100
    ): Boolean {
        // region LOG
            dLog(TAG, "resizeImagePreserveAspect(path = $path, maxW = $maxWidth, maxH = $maxHeight, format = ${format.name}, quality = $quality)")
        // endregion
        val original = getBitmapFromPath(path) ?: return false
        
        val width = original.width
        val height = original.height
        
        if (width <= maxWidth && height <= maxHeight) return true //\/\/\/\/\
        
        val ratio = minOf(maxWidth / width.toFloat(), maxHeight / height.toFloat())
        val newW = (width * ratio).toInt()
        val newH = (height * ratio).toInt()
        
        val resized = original.scale(newW, newH)
        
        return saveBitmapAt(path, resized, format, quality)
    }
    
    
    fun renameImage(                                                             oldPath: String,
                                                                                 newName: String
    ): String? {
        // region LOG
            dLog(TAG, "renameImage(oldPath = $oldPath, newName = $newName)")
        // endregion
        if (oldPath.isBlank() || newName.isBlank()) return null //\/\/\
        
        val oldFile = getFile(oldPath)
        if (!oldFile.exists()) return null //\/\/\
        
        val extension = oldFile.extension.ifBlank { "img" }
        val newFile = File(imagesDir, "$newName.$extension")
        
        return if (oldFile.renameTo(newFile)) { "$picFolderName/${newFile.name}" } else null //\/\/\
    }
    
    
    
    
}






