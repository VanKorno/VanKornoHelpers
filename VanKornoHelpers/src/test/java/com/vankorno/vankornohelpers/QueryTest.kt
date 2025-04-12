package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.sql.LibConstantsDB.*
import com.vankorno.vankornohelpers.sql.getQuery
import junit.framework.TestCase.assertEquals
import org.junit.Test

private const val DirtyTable = "DirtyTable"
private const val BestName = "BestName"

class QueryTest {
    
    @Test
    fun `Simple checks`() {
        assertEquals(
            selectAllFrom + DirtyTable,
            getQuery(DirtyTable).first
        )
        assertEquals(
            selectAllFrom + DirtyTable + orderBy + ID,
            getQuery(DirtyTable, orderBy = ID).first
        )
        assertEquals(
            selectAllFrom + DirtyTable + where + ID+"=?" + orderBy + ID+c + Name,
            getQuery(DirtyTable, conditions={equals(ID, 1)}, orderBy=ID+c + Name).first
        )
        
        assertEquals(
            select + ID+c + Name + from + DirtyTable + where + ID+">=?",
            getQuery(
                table = DirtyTable,
                columns = arrayOf(ID, Name),
                conditions = {
                    greaterOrEq(ID, 10)
                }
            ).first
        )
        assertEquals(
            selectAllFrom + DirtyTable + where + ID+">?",
            getQuery(
                table = DirtyTable,
                conditions = { greaterThan(ID, 1) }
            ).first
        )
        assertEquals(
            selectAllFrom + DirtyTable + where + ID+"<?",
            getQuery(
                table = DirtyTable,
                conditions = { lessThan(ID, 1) }
            ).first
        )
        assertEquals(
            selectAllFrom + DirtyTable + where + ID+"<=?",
            getQuery(
                table = DirtyTable,
                conditions = { lessOrEq(ID, 1) }
            ).first
        )
        assertEquals(
            selectAllFrom + DirtyTable + where + Bool1+"=?",
            getQuery(
                table = DirtyTable,
                conditions = { equals(Bool1, true) }
            ).first
        )
    }
    
    @Test
    fun `Simple AND OR conditions`() {
        assertEquals(
            selectAllFrom + DirtyTable + where + ID+">=?" + and + Name+"=?",
            getQuery(
                table = DirtyTable,
                conditions = {
                    greaterOrEq(ID, 10)
                    and {
                        equals(Name, BestName)
                    }
                }
            ).first
        )
        
        assertEquals(
            selectAllFrom + DirtyTable +
                where + ID+">=?" +
                and + Name+"=?" +
                and + Priority+"=?" +
                and + ID+"=?",
            getQuery(
                table = DirtyTable,
                conditions = {
                    greaterOrEq(ID, 10)
                    and {
                        equals(Name, BestName)
                    }
                    and {
                        equals(Priority, 1.1F)
                    }
                    and {
                        equals(ID, BestName)
                    }
                }
            ).first
        )
        
        assertEquals(
            selectAllFrom + DirtyTable +
                where + ID+">=?" +
                and + Name+"=?" +
                or + Priority+"=?" +
                or + ID+"=?",
            getQuery(
                table = DirtyTable,
                conditions = {
                    greaterOrEq(ID, 10)
                    and {
                        equals(Name, BestName)
                    }
                    or {
                        equals(Priority, 1.1F)
                    }
                    or {
                        equals(ID, 1)
                    }
                }
            ).first
        )
    }
    
    @Test
    fun `condition values`() {
        assertEquals(
            arrayOf("1", "1", "1.1", "1").joinToString(", "),
            getQuery(
                table = DirtyTable,
                conditions = {
                    greaterOrEq(ID, "1")
                    and {
                        equals(ID, 1)
                    }
                    and {
                        greaterOrEq(ID, 1.1F)
                    }
                    and {
                        greaterOrEq(ID, 1L)
                    }
                }
            ).second.joinToString(", ")
        )
    }
    
    
    
    @Test
    fun `Two lvl conditions orGroup is last`() {
        assertEquals(
            selectAllFrom + DirtyTable +
                where + ID+">=?" +
                and + Name+"=?" +
                or + "("+Priority+"=?" + or + ID+"=?)",
            
            getQuery(
                DirtyTable,
                conditions = {
                    greaterOrEq(ID, 10)
                    and { equals(Name, BestName) }
                    orGroup {
                        equals(Priority, 1.1F)
                        or { equals(ID, 1) }
                    }
                }
            ).first
        )
    }
    
    @Test
    fun `Two lvl conditions group{} is first`() {
        assertEquals(
            selectAllFrom + DirtyTable +
                where + "("+ID+">=?" + and + Name+"=?)" +
                and + Priority+"=?" + 
                or + ID+"=?"
            ,
            getQuery(
                DirtyTable,
                conditions = {
                    group {
                        greaterOrEq(ID, 10)
                        and { equals(Name, BestName) }
                    }
                    and { equals(Priority, 1.1F) }
                    or { equals(ID, 1) }
                }
            ).first
        )
    }
    
    @Test
    fun `Two lvl conditions andGroup is mid`() {
        assertEquals(
            selectAllFrom + DirtyTable +
                where + 
                Priority+"=?" +
                and +
                    "("+ID+">=?" + and + Name+"=?)" +
                and + Priority+"=?" + 
                or + ID+"=?"
            ,
            getQuery(
                DirtyTable,
                conditions = {
                    equals(Priority, 1.1F)
                    andGroup {
                        greaterOrEq(ID, 10)
                        and { equals(Name, BestName) }
                    }
                    and { equals(Priority, 1.1F) }
                    or { equals(ID, 1) }
                }
            ).first
        )
    }
    
    
    @Test
    fun `Five lvl conditions`() {
        val result = getQuery(
            DirtyTable,
            conditions = {
                equals(Priority, 1)
                andGroup {
                    greaterOrEq(ID, 2)
                    orGroup {
                        equals(Name, BestName)
                        andGroup {
                            equals(Name, BestName)
                            orGroup {
                                equals(Name, BestName)
                                or { lessThan(Priority, 5) }
                            }
                        }
                    }
                }
                and { equals(Priority, 1.1F) }
                or { equals(ID, 1) }
            }
        )
        
        assertEquals(
            selectAllFrom + DirtyTable +
                where + 
                Priority+"=?" +
                and + "("+
                    ID+">=?" +
                    or + "("+
                        Name+"=?"+
                        and + "("+
                            Name+"=?"+
                            or + "("+
                                Name+"=?"+
                                or + Priority+"<?" +
                            ")" +
                        ")" +
                    ")" +
                ")" +
                and + Priority+"=?" + 
                or + ID+"=?"
            ,
            result.first
        )
        assertEquals(
            arrayOf("1", "2", BestName, BestName, BestName, "5", "1.1", "1").joinToString(", "),
            result.second.joinToString(", ")
        )
        assertEquals(
            "SELECT * FROM DirtyTable WHERE Priority=? AND (ID>=? OR (Name=? AND (Name=? OR (Name=? OR Priority<?)))) AND Priority=? OR ID=?",
            result.first
        )
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}