package com.vankorno.vankornohelpers.sql

import android.database.sqlite.SQLiteDatabase

object DbManager {
    private lateinit var _mainDb: SQLiteDatabase
    
    val mainDb: SQLiteDatabase
        get() {
            if (!::_mainDb.isInitialized) {
                throw IllegalStateException("Database not initialized. Ensure LibDBHelper is properly set up.")
            }
            return _mainDb
        }

    fun init(helper: LibDBHelper) {
        _mainDb = helper.writableDatabase
    }

    fun close() {
        if (::_mainDb.isInitialized && _mainDb.isOpen)
            _mainDb.close()
    }
}