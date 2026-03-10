package com.vankorno.vankornohelpers.fileOps

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class FileOps(                                                       private val context: Context,
                                                                     private val dirName: String,
                                                                         private val dir: File,
) {
    fun list(): List<String> = dir.listFiles()?.map { "$dirName/${it.name}" }.orEmpty()
    
    
    fun deleteAll(): Int = dir.listFiles()?.count { it.delete() } ?: 0
    
    
    fun exists(name: String): Boolean = File(dir, name).exists()
    
    
    fun delete(                                                                     name: String
    ): Boolean {
        val file = File(dir, name)
        return file.exists() && file.delete()
    }
    
    
    fun rename(                                                                  oldName: String,
                                                                                 newName: String,
    ): String? {
        val oldFile = File(dir, oldName)
        if (!oldFile.exists()) return null //\/\/\/\/\/\
        
        val ext = oldFile.extension
        val newFile = File(
            dir,
            if (ext.isBlank()) newName else "$newName.$ext"
        )
        
        return  if (oldFile.renameTo(newFile))
                    "$dirName/${newFile.name}"
                else
                    null
    }
    
    
    fun writeText(                                                                  name: String,
                                                                                    text: String,
    ): String {
        val file = File(dir, name)
        file.writeText(text)
        return "$dirName/$name"
    }
    
    fun readText(                                                                   name: String
    ): String? = try {
        File(dir, name).readText()
    } catch (e: Exception) { null }
    
    
    fun saveFromUri(                                                                 uri: Uri,
                                                                                filename: String,
    ): String? {
        val file = File(dir, filename)
        
        return try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { out ->
                    input.copyTo(out)
                }
            } ?: return null
            
            "$dirName/$filename"
        }
        catch (e: Exception) {
            null
        }
    }
    
}