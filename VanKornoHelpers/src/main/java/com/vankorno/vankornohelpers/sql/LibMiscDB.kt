package com.vankorno.vankornohelpers.sql

import com.vankorno.vankornohelpers.sql.DbManager.db
import com.vankorno.vankornohelpers.sql.LibConstantsDB.*

class LibMiscDB() {
    
    fun deleteFirstRow(                                                      whichTable: String
    ) {
        val whereClause = RowID + " = (" + select + RowID + from + whichTable+ limit + "1)"
        db.delete(whichTable, whereClause, null)
    }
    
    
    
    
    
    
    
    
    
}