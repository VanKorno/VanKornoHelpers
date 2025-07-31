package com.vankorno.vankornohelpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.scale
import java.io.File
import java.io.FileOutputStream

private const val TAG = "LibPic"

class LibPic(             private val context: Context,
                          private val nameGen: ()->String = { "pic_${System.currentTimeMillis()}" },
                    private val picFolderName: String = "user_pics"
) {
    val imagesDir = File(context.filesDir, picFolderName).apply { mkdirs() }
    
    
    fun getAbsolutePath(path: String): String = getFileInAppStorage(context, path).absolutePath
    
    
    fun saveImageFromUri(                                                      uri: Uri,
                                                                         extension: String = "png"
    ): String {
        // region LOG
            dLog(TAG, "saveImageFromUri()")
        // endregion
        val filename = generateUniqueFilename(extension)
        val outFile = File(imagesDir, filename)
        
        val inputStream = context.contentResolver.openInputStream(uri)
        if (inputStream == null) {
            // region LOG
            eLog(TAG, "saveImageFromUri(): Unable to open input stream for $uri. Returning empty string.")
            // endregion
            return ""
        }
        inputStream.use { input ->
            FileOutputStream(outFile).use { output ->
                input.copyTo(output)
            }
        }
        return "$picFolderName/$filename"
    }
    
    
    fun deleteImage(                                                                path: String
    ): Boolean {
        // region LOG
            dLog(TAG, "deleteImage(path = $path)")
        // endregion
        if (path.isBlank()) {
            // region LOG
                eLog(TAG, "deleteImage(): path is blank. Returning...")
            // endregion
            return false //\/\/\/\/\/\ 
        }
        val file = getFileInAppStorage(context, path)
        return file.exists() && file.delete()
    }
    
    
    fun listImageFiles(): List<File> = imagesDir.listFiles()?.toList().orEmpty()
    
    
    fun clearAllImages(): Int {
        // region LOG
            dLog(TAG, "clearAllImages()")
        // endregion
        return imagesDir.listFiles()?.count { it.delete() } ?: 0
    }
    
    
    fun getImageSize(                                                               path: String
    ): SizeWH? {
        return try {
            val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeFile(getFileInAppStorage(context, path).absolutePath, opts)
            val w = opts.outWidth
            val h = opts.outHeight
            // region LOG
                dLog(TAG, "getImageSize() returns w = $w, h = $h")
            // endregion
            SizeWH(w = w, h = h)
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
            // region LOG
                dLog(TAG, "updateImageFromUri(): Updated successfully!")
            // endregion
            true
        } catch (e: Exception) {
            // region LOG
                eLog(TAG, "updateImageFromUri(): Update failed!", e)
            // endregion
            false
        }
    }
    
    
    fun getBitmapFromPath(                                                          path: String
    ): Bitmap? = BitmapFactory.decodeFile(
        getFileInAppStorage(context, path).absolutePath
    )
    
    
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
        val filename = generateUniqueFilename(extension)
        
        saveBitmapAt(filename, bitmap, format, quality)
        
        return "$picFolderName/$filename"
    }
    
    
    fun saveBitmapAt(                       path: String,
                                          bitmap: Bitmap,
                                          format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                                         quality: Int = 100
    ): Boolean {
        // region LOG
            dLog(TAG, "saveBitmapAt(path = $path, format = ${format.name}, quality = $quality)")
        // endregion
        if (path.isBlank()) {
            // region LOG
                eLog(TAG, "saveBitmapAt() Failed because path is blank or empty.")
            // endregion
            return false //\/\/\/\/\/\
        }
        return try {
            val file = getFileInAppStorage(context, path)
            
            file.parentFile?.mkdirs() /*This ensures that
                if someone ever passes a path with subdirectories (e.g., "subfolder/my_image.png"),
                it won’t crash with FileNotFoundException.*/
            
            FileOutputStream(file).use { out ->
                bitmap.compress(format, quality, out)
            }
            // region LOG
                dLog(TAG, "saveBitmapAt(): Saved successfully!")
            // endregion
            true
        } catch (e: Exception) {
            // region LOG
                eLog(TAG, "saveBitmapAt() Failed!", e)
            // endregion
            false
        }
    }
    
    
    
    
    
    // ------------------------------------  R E S I Z E  ------------------------------------ \\
    
    /** fraction: 1f = 100% */
    fun resizePicToScr(                     path: String,
                                        fraction: Float = 1f,
                                         quality: Int = 100,
                                          format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
                                 longestSideOnly: Boolean = true
    ): Boolean {
        // region LOG
            dLog(TAG, "resizePicToScr(path = $path, fraction = $fraction, quality = $quality, format = ${format.name}, byLongestSideOnly = $longestSideOnly)")
        // endregion
        val (maxW, maxH) = getMaxWHToFitScr(fraction, longestSideOnly)
        return resizeImagePreserveAspect(path, maxW, maxH, format, quality)
    }
    
    
    fun resizeBitmapToScr(                                                original: Bitmap,
                                                                          fraction: Float = 1f,
                                                                   longestSideOnly: Boolean = true
    ): Bitmap {
        // region LOG
            dLog(TAG, "resizeBitmapToScr(fraction = $fraction, longestSideOnly = $longestSideOnly)")
        // endregion
        val (maxW, maxH) = getMaxWHToFitScr(fraction, longestSideOnly)
        val result = resizeBitmapPreserveAspect(original, maxW, maxH)
        return if (result.resized) result.bitmap else original
    }
    
    
    private fun getMaxWHToFitScr(                                               fraction: Float,
                                                                         longestSideOnly: Boolean
    ): Pair<Int, Int> {
        val safeFraction = fraction.coerceIn(0.1f, 1f)
        val screen = getRealScreenSizePx(context)
        var maxW = (screen.w * safeFraction).toInt()
        var maxH = (screen.h * safeFraction).toInt()
        
        if (longestSideOnly) {
            val longest = maxOf(maxW, maxH)
            maxW = longest
            maxH = longest
        }
        return Pair(maxW, maxH)
    }
    
    
    /**
     * Returns true if resized successfully or already small enough.
     */
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
        val result = resizeBitmapPreserveAspect(original, maxWidth, maxHeight)
        if (!result.resized) return true //\/\/\/\/\/\
        
        return saveBitmapAt(path, result.bitmap, format, quality)
    }
    
    private fun resizeBitmapPreserveAspect(                                     original: Bitmap,
                                                                                maxWidth: Int,
                                                                               maxHeight: Int
    ): ResizeBitmapResult {
        val width = original.width
        val height = original.height
        
        if (width <= maxWidth && height <= maxHeight) {
            // region LOG
            dLog(TAG, "resizeBitmapPreserveAspect(): The image is already small enough. Resizing isn't needed.")
            // endregion
            return ResizeBitmapResult(false, original) //\/\/\/\/\/\ 
        }
        val ratio = minOf(maxWidth / width.toFloat(), maxHeight / height.toFloat())
        val newW = (width * ratio).toInt()
        val newH = (height * ratio).toInt()
        val resized = original.scale(newW, newH)
        return ResizeBitmapResult(true, resized)
    }
    
    internal data class ResizeBitmapResult(val resized: Boolean, val bitmap: Bitmap)
    
    
    
    
    
    // ------------------------------------  N A M I N G  ------------------------------------ \\
    
    fun renameImage(                                                             oldPath: String,
                                                                                 newName: String
    ): String? {
        // region LOG
            dLog(TAG, "renameImage(oldPath = $oldPath, newName = $newName)")
        // endregion
        if (oldPath.isBlank()) {
            // region LOG
                eLog(TAG, "renameImage(): oldPath is blank. Returning null...")
            // endregion
            return null //\/\/\/\/\/\
        } else if (newName.isBlank()) {
            // region LOG
                eLog(TAG, "renameImage(): newName is blank. Returning null...")
            // endregion
            return null //\/\/\/\/\/\
        }
        val oldFile = getFileInAppStorage(context, oldPath)
        if (!oldFile.exists()) {
            // region LOG
                eLog(TAG, "renameImage(): The old file doesn't exist! Returning null...")
            // endregion
            return null //\/\/\/\/\/\
        }
        val extension = oldFile.extension.ifBlank { "img" }
        val newFile = File(imagesDir, "$newName.$extension")
        
        return if (oldFile.renameTo(newFile)) {
            val newPath = "$picFolderName/${newFile.name}"
            // region LOG
                dLog(TAG, "renameImage(): Successfully renamed the file $newPath")
            // endregion
            newPath
        } else {
            // region LOG
                eLog(TAG, "renameImage(): Renaming failed. Returning null...")
            // endregion
            null
        }
    }
    
    
    private fun generateUniqueFilename(                                        extension: String
    ): String {
        val base = nameGen()
        var filename = "$base.$extension"
        var counter = 1
        
        while (File(imagesDir, filename).exists()) {
            filename = "${base}_$counter.$extension"
            counter++
        }
        return filename
    }
    
    
}






