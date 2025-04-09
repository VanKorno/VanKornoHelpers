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
                db.execSQL(libMiscDB.buildQuery(it.tableName, it.entity))
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
            Log.d(TAG, "onUpgrade() Migrating...")
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
    
    fun getInt(                                                  tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Int = readWriteDB("getInt()", 0) { 
        LibGetSetDB(db).getInt(tableName, column, whereClause, whereArg)
    }
    
    fun getStr(                                                  tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): String = readWriteDB("getStr()", "") { 
        LibGetSetDB(db).getStr(tableName, column, whereClause, whereArg)
    }
    
    fun getBool(                                                 tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Boolean = readWriteDB("getBool()", false) { 
        LibGetSetDB(db).getBool(tableName, column, whereClause, whereArg)
    }
    
    fun getLong(                                                 tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Long = readWriteDB("getLong()", 0L) { 
        LibGetSetDB(db).getLong(tableName, column, whereClause, whereArg)
    }
    
    fun getFloat(                                                tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Float = readWriteDB("getFloat()", 0F) { 
        LibGetSetDB(db).getFloat(tableName, column, whereClause, whereArg)
    }
    
    
    
    
    
    // ============================== SETTERS ===========================================
    
    fun setStr(                                                      value: String,
                                                                 tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("setStr()") {
            LibGetSetDB(db).setStr(value, tableName, column, whereClause, whereArg)
        }
    }
    fun setInt(                                                      value: Int,
                                                                 tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("setInt()") {
            LibGetSetDB(db).setInt(value, tableName, column, whereClause, whereArg)
        }
    }
    fun setBool(                                                     value: Boolean,
                                                                 tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("setBool()") {
            LibGetSetDB(db).setBool(value, tableName, column, whereClause, whereArg)
        }
    }
    fun setLong(                                                     value: Long,
                                                                 tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("setLong()") {
            LibGetSetDB(db).setLong(value, tableName, column, whereClause, whereArg)
        }
    }
    fun setFloat(                                                    value: Float,
                                                                 tableName: String,
                                                                    column: String,
                                                               whereClause: String,
                                                                  whereArg: String,
                                                                        db: SQLiteDatabase = mainDb
    ) {
        writeDB("setFloat()") {
            LibGetSetDB(db).setFloat(value, tableName, column, whereClause, whereArg)
        }
    }
    
    
    
    
    
    fun checkIfEmpty(                                            tableName: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Boolean = readWriteDB("checkIfEmpty()", false) { 
        LibGetSetDB(db).isEmpty(tableName)
    }
    
    fun getLastID(                                               tableName: String,
                                                                        db: SQLiteDatabase = mainDb
    ): Int = readWriteDB("getLastID()", 0) { LibGetSetDB(db).getLastID(tableName) }
    
    
    
    fun deleteFirstRow(                                          tableName: String,
                                                                        db: SQLiteDatabase = mainDb
    ) = writeDB("deleteFirstRow()") {
        LibMiscDB(db).deleteFirstRow(tableName)
    }
    
    
    
    
    
    
}