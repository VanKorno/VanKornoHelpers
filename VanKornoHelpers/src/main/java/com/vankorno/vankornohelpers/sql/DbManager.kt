package com.vankorno.vankornohelpers.sql

import android.database.sqlite.SQLiteDatabase

object DbManager {
    private var _mainDb: SQLiteDatabase? = null
    
    val mainDb: SQLiteDatabase
        get() = _mainDb ?: throw IllegalStateException("Database not initialized.")
    
    fun init(helper: LibDBHelper) {
        _mainDb = helper.writableDatabase
    }
    
    fun close() {
        _mainDb?.let {
            if (it.isOpen) it.close()
        }
        _mainDb = null
    }
}