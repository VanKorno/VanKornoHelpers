package com.vankorno.vankornohelpers.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.vankorno.vankornohelpers.sql.DbManager.db
import com.vankorno.vankornohelpers.sql.LibConstantsDB.*
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
    
    
    override fun onCreate(                                                 dbLocal: SQLiteDatabase
    ) {
        // region LOG
            Log.d(TAG, "onCreate runs")
        // endregion
        synchronized(dbLock) {
            onCreateStart(dbLocal)
            
            entities.forEach {
                dbLocal.execSQL(buildQuery(it.whichTable, it.entity))
            }
            onCreateFinish(dbLocal)
        }
    }
    
    
    override fun onUpgrade(                                                dbLocal: SQLiteDatabase,
                                                                        oldVersion: Int,
                                                                        newVersion: Int
    ) {
        if (oldVersion >= newVersion)  return  //\/\/\/\/\/\
        // region LOG
            Log.d(TAG, "onUpgrade runs")
        // endregion
        synchronized(dbLock) {
            onUpgrade(dbLocal)
        }
    }
    
    fun initializeDbManager() {
        // region LOG
            Log.d(TAG, "initializeDbManager() runs")
        // endregion
        DbManager.init(this)
    }
    
    
    fun buildQuery(                                             whichTable: String,
                                                                    entity: ArrayList<Array<String>>
    ): String {
        var queryStr = dbCreateT + whichTable + dbAutoID
        val last = entity.size - 1
        
        for (idx in 1 until last) {
            queryStr += entity[idx][0] + entity[idx][1] + c
        }
        return queryStr + entity[last][0] + entity[last][1] + ")"
    }
    
    
    
    
    
    // -------------------------------------------------------------------------------------------
    
    inline fun writeDB(                                                           logTxt: String,
                                                                                     run: ()->Unit
    ) {
        synchronized(dbLock) {
            try {
                db.use { run() }
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
                        db.use { action() }
                    } catch (e: Exception) {
                        // region LOG
                            Log.e("LibDBHelper", "Error: $logTxt  $e")
                        // endregion
                        defaultValue
                    }
        }
    }
    
    
    
    // ============================== GETTERS ===========================================
    
    fun getInt(                                                               whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): Int = readWriteDB("getInt()", 0) { 
        LibGetSetDB().getInt(whichTable, column, whereClause, whereArg)
    }
    
    
    fun getString(                                                            whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): String = readWriteDB("getString()", "") { 
        LibGetSetDB().getString(whichTable, column, whereClause, whereArg)
    }
    
    fun getBool(                                                              whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): Boolean = readWriteDB("getBool()", false) { 
        LibGetSetDB().getBool(whichTable, column, whereClause, whereArg)
    }
    
    fun getLong(                                                              whichTable: String,
                                                                                  column: String,
                                                                             whereClause: String,
                                                                                whereArg: String
    ): Long = readWriteDB("getLong()", 0L) { 
        LibGetSetDB().getLong(whichTable, column, whereClause, whereArg)
    }
    
    // ============================== SETTERS ===========================================
    
    fun set(                                                                    value: String,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        writeDB("set()") {
            LibGetSetDB().set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                                     value: Int,
                                                                            whichTable: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ) {
        writeDB("set()") {
            LibGetSetDB().set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                                    value: Boolean,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        writeDB("set()") {
            LibGetSetDB().set(value, whichTable, column, whereClause, whereArg)
        }
    }
    fun set(                                                                    value: Long,
                                                                           whichTable: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        writeDB("set()") {
            LibGetSetDB().set(value, whichTable, column, whereClause, whereArg)
        }
    }
    
    
    
    
    
    fun checkIfEmpty(                                                         whichTable: String
    ): Boolean = readWriteDB("checkIfEmpty()", false) { 
        LibGetSetDB().isEmpty(whichTable)
    }
    
    fun getLastID(                                                            whichTable: String
    ): Int = readWriteDB("getLastID()", 0) { LibGetSetDB().getLastID(whichTable) }
    
    
    
    fun deleteFirstRow(whichTable: String) = writeDB("deleteFirstRow()") {
        LibMiscDB().deleteFirstRow(whichTable)
    }
    
    
    
    
    
    
}