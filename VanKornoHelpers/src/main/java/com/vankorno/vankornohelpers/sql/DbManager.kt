package com.vankorno.vankornohelpers.sql

import android.database.sqlite.SQLiteDatabase

object DbManager {
    private lateinit var _db: SQLiteDatabase
    
    val db: SQLiteDatabase
        get() {
            if (!::_db.isInitialized) {
                throw IllegalStateException("Database not initialized. Ensure LibDBHelper is properly set up.")
            }
            return _db
        }

    fun init(helper: LibDBHelper) {
        _db = helper.writableDatabase
    }

    fun close() {
        if (::_db.isInitialized && _db.isOpen)
            _db.close()
    }
}