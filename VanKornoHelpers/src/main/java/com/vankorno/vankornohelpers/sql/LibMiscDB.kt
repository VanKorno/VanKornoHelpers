package com.vankorno.vankornohelpers.sql

import android.database.sqlite.SQLiteDatabase
import com.vankorno.vankornohelpers.sql.LibConstantsDB.*

class LibMiscDB(val db: SQLiteDatabase) {
    
    fun deleteFirstRow(                                                       tableName: String
    ) {
        val whereClause = RowID + " = (" + select + RowID + from + tableName+ limit + "1)"
        db.delete(tableName, whereClause, null)
    }
    
    fun buildQuery(                                              tableName: String,
                                                                    entity: ArrayList<Array<String>>
    ): String {
        var queryStr = dbCreateT + tableName + dbAutoID
        val last = entity.size - 1
        
        for (idx in 1 until last) {
            queryStr += entity[idx][0] + entity[idx][1] + c
        }
        return queryStr + entity[last][0] + entity[last][1] + ")"
    }
    
    
    
    
    
    
    
    
}