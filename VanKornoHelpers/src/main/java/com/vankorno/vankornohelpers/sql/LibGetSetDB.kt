package com.vankorno.vankornohelpers.sql

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.vankorno.vankornohelpers.getBool
import com.vankorno.vankornohelpers.sql.LibConstantsDB.*
import com.vankorno.vankornohelpers.values.LibConstants.iOFF

private const val TAG = ""

open class LibGetSetDB(val db: SQLiteDatabase) {
    
    // ============================== GETTERS ===========================================
    
    fun getInt(                                                              tableName: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ): Int {
        val cursor = db.rawQuery(select + column + from + tableName + where + whereClause+"=?",
                                                                               arrayOf(whereArg) )
        val mySocks =   if (cursor.moveToFirst())
                            cursor.getInt(0)
                        else {
                            // region LOG
                            Log.e(TAG, "getInt() Unable to get value from DB. Returning zero")
                            // endregion
                            0
                        }
        cursor.close()
        return mySocks
    }
    
    fun getStr(                                                              tableName: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ): String {
        val cursor = db.rawQuery(select + column + from + tableName + where + whereClause+"=?",
                                                                                arrayOf(whereArg) )
        val mySocks =   if (cursor.moveToFirst())
                            cursor.getString(0)
                        else {
                            // region LOG
                            Log.e(TAG, "getString() Unable to get value from DB. Returning an empty str")
                            // endregion
                            ""
                        }
        cursor.close()
        return mySocks
    }
    fun getBool(                                                              tableName: String,
                                                                                 column: String,
                                                                            whereClause: String,
                                                                               whereArg: String
    ): Boolean {
        val cursor = db.rawQuery(select + column + from + tableName + where + whereClause+"=?",
                                                                              arrayOf(whereArg) )
        val mySocks =   if (cursor.moveToFirst())
                            cursor.getBool(0)
                        else {
                            // region LOG
                            Log.e(TAG, "getBool() Unable to get value from DB. Returning FALSE")
                            // endregion
                            false
                        }
        cursor.close()
        return mySocks
    }
    fun getLong(                                                             tableName: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ): Long {
        val cursor = db.rawQuery(select + column + from + tableName + where + whereClause+"=?",
                                                                              arrayOf(whereArg) )
        val mySocks =   if (cursor.moveToFirst())
                            cursor.getLong(0)
                        else {
                            // region LOG
                            Log.e(TAG, "getLong() Unable to get value from DB. Returning zero")
                            // endregion
                            0L
                        }
        cursor.close()
        return mySocks
    }
    fun getFloat(                                                             tableName: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ): Float {
        val cursor = db.rawQuery(select + column + from + tableName + where + whereClause+"=?",
                                                                              arrayOf(whereArg) )
        val mySocks =   if (cursor.moveToFirst())
                            cursor.getFloat(0)
                        else {
                            // region LOG
                            Log.e(TAG, "getFloat() Unable to get value from DB. Returning zero")
                            // endregion
                            0F
                        }
        cursor.close()
        return mySocks
    }
    
    
    
    // ----------------------------------------------------------------------------------
    
    fun getLastID(                                                            tableName: String
    ): Int {
        val cursor = db.rawQuery(select + ID + from + tableName, null)
        
        val mySocks = if (cursor.moveToLast())
                            cursor.getInt(0)
                        else{
                            // region LOG
                            Log.e(TAG, "getLastID() Unable to get value from DB. Returning $iOFF")
                            // endregion
                            iOFF
                        }
        cursor.close()
        return mySocks
    }
    
    fun getAllIDs(                                                           tableName: String,
                                                                            orderByStr: String = ""
    ): MutableList<Int> {
        val cursor = db.rawQuery(select + ID + from + tableName + orderByStr,   null)
        val ids = mutableListOf<Int>()
        
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ids
    }
    
    fun isEmpty(                                                               tableName: String
    ): Boolean {
        val cursor = db.rawQuery(selectAllFrom + tableName, null)
        val emptiness = cursor.count < 1
        cursor.close()
        return emptiness
    }
    
    fun getNewPriority(                                                        tableName: String
    ): Int {
        val cursor = db.rawQuery(select + Priority + from + tableName + orderBy + Priority,  null)
        var priority =  if (cursor.moveToLast())
                            cursor.getInt(0) + 1
                        else
                            1
        cursor.close()
        return priority
    }
    
    
    
    // ============================== SETTERS ===========================================
    
    fun setStr(                                                                 value: String,
                                                                            tableName: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(tableName, cv, whereClause+"=?", arrayOf(whereArg))
    }
    fun setInt(                                                                  value: Int,
                                                                             tableName: String,
                                                                                column: String,
                                                                           whereClause: String,
                                                                              whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(tableName, cv, whereClause+"=?", arrayOf(whereArg))
    }
    fun setBool(                                                                value: Boolean,
                                                                            tableName: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(tableName, cv, whereClause+"=?", arrayOf(whereArg))
    }
    fun setLong(                                                                value: Long,
                                                                            tableName: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(tableName, cv, whereClause+"=?", arrayOf(whereArg))
    }
    fun setFloat(                                                               value: Float,
                                                                            tableName: String,
                                                                               column: String,
                                                                          whereClause: String,
                                                                             whereArg: String
    ) {
        val cv = ContentValues()
        cv.put(column, value)
        db.update(tableName, cv, whereClause+"=?", arrayOf(whereArg))
    }
    
    
    
    
    
    
}