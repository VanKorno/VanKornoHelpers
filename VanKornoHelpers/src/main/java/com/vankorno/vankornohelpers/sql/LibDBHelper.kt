package com.vankorno.vankornohelpers.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.vankorno.vankornohelpers.sql.DbManager.mainDb
import com.vankorno.vankornohelpers.sql.entt.LibDbTableAndEntt

private const val TAG = "LibDBHelper"

open class LibDBHelper(               context: Context,
                                       dbName: String,
                                    dbVersion: Int,
                         private val entities: Array<LibDbTableAndEntt> = emptyArray<LibDbTableAndEntt>(),
                            val onCreateStart: (SQLiteDatabase)->Unit = {},
                           val onCreateFinish: (SQLiteDatabase)->Unit = {},
                                val onUpgrade: (SQLiteDatabase)->Unit = {}
) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
    val dbLock = Any()
    
    
    override fun onCreate(                                                      db: SQLiteDatabase
    ) {
        // region LOG
            Log.d("LibDBHelper", "onCreate runs")
        // endregion
        synchronized(dbLock) {
            onCreateStart(db)
            
            val libMiscDB = LibMiscDB(db)
            entities.forEach {
                db.execSQL(libMiscDB.buildQuery(it.whichTable, it.entity))
            }
            onCreateFinish(db)
        }
    }
    
    
    override fun onUpgrade(                                                     db: SQLiteDatabase,
                                                                        oldVersion: Int,
                                                                        newVersion: Int
    ) {
        if (oldVersion >= newVersion)  return  //\/\/\/\/\/\
        // region LOG
            Log.d(TAG, "onUpgrade runs")
        // endregion
        synchronized(dbLock) {
            onUpgrade(db)
        }
    }
    
    fun initializeDbManager() {
        // region LOG
            Log.d(TAG, "initializeDbManager() runs")
        // endregion
        DbManager.init(this)
    }
    
    
    
    
    
    
    
    // -------------------------------------------------------------------------------------------
    
    inline fun writeDB(                                                         logTxt: String,
                                                                                   run: () -> Unit
    ) {
        synchronized(dbLock) {
            try {
                run()
            } catch (e: Exception) {
                // region LOG
                    Log.e("LibDBHelper", "Error: $logTxt  $e")
                // endregion
            }
        }
    }
    
    inline fun <T> readWriteDB(                                                   logTxt: String,
                                                                            defaultValue: T,
                                                                                  action: () -> T
    ): T {
        synchronized(dbLock) {
            return  try {
                        action()
                    } catch (e: Exception) {
                        // region LOG
                            Log.e("LibDBHelper", "Error: $logTxt  $e")
                        // endregion
                        defaultValue
                    }
        }
    }
    
    
    
    // ============================== GETTERS ===========================================
    
    fun getInt(                                                 whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Int = readWriteDB("getInt()", 0) { 
        LibGetSetDB(db).getInt(whichTable, column, whereClause, whereArg)
    }
    
    
    fun getString(                                              whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): String = readWriteDB("getString()", "") { 
        LibGetSetDB(db).getString(whichTable, column, whereClause, whereArg)
    }
    
    fun getBool(                                                whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Boolean = readWriteDB("getBool()", false) { 
        LibGetSetDB(db).getBool(whichTable, column, whereClause, whereArg)
    }
    
    fun getLong(                                                whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Long = readWriteDB("getLong()", 0L) { 
        LibGetSetDB(db).getLong(whichTable, column, whereClause, whereArg)
    }
    
    // ============================== SETTERS ===========================================
    
    fun set(                                                         value: String,
                                                                whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("set()") {
            LibGetSetDB(db).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                         value: Int,
                                                                whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("set()") {
            LibGetSetDB(db).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                         value: Boolean,
                                                                whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("set()") {
            LibGetSetDB(db).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                         value: Long,
                                                                whichTable: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("set()") {
            LibGetSetDB(db).set(value, whichTable, column, whereClause, whereArg)
        }
    }
    
    
    
    
    
    fun checkIfEmpty(                                           whichTable: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Boolean = readWriteDB("checkIfEmpty()", false) { 
        LibGetSetDB(db).isEmpty(whichTable)
    }
    
    fun getLastID(                                              whichTable: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Int = readWriteDB("getLastID()", 0) { LibGetSetDB(db).getLastID(whichTable) }
    
    
    
    fun deleteFirstRow(                                         whichTable: String,
                                                                        db: SQLiteDatabase = mainDb
    ) = writeDB("deleteFirstRow()") {
        LibMiscDB(db).deleteFirstRow(whichTable)
    }
    
    
    
    
    
    
}