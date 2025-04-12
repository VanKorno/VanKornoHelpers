package com.vankorno.vankornohelpers.sql

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

fun SQLiteDatabase.query(                                   table: String,
                                                          columns: Array<out String> = arrayOf("*"),
                                                       conditions: CondBuilder.()->Unit = {},
                                                          orderBy: String? = null,
                                                           extras: String? = null
): Cursor {
    val (query, args) = getQuery(table, columns, conditions, orderBy, extras)
    return rawQuery(query, args)
}


fun getQuery(                                               table: String,
                                                          columns: Array<out String> = arrayOf("*"),
                                                       conditions: CondBuilder.()->Unit = {},
                                                          orderBy: String? = null,
                                                           extras: String? = null
): Pair<String, Array<String>> {
    val where = CondBuilder().apply(conditions)
    val query = buildString {
        append("SELECT ")
        append(columns.joinToString(", "))
        append(" FROM ")
        append(table)
        if (where.clauses.isNotEmpty()) {
            append(" WHERE ")
            append(where.clauses.joinToString(" "))
        }
        if (orderBy != null) {
            append(" ORDER BY ")
            append(orderBy)
        }
        if (extras != null) {
            append(extras)
        }
    }
    return query to where.args.toTypedArray()
}


class CondBuilder {
    val clauses = mutableListOf<String>()
    val args = mutableListOf<String>()
    
    fun condition(                                                                column: String,
                                                                                operator: String,
                                                                                   value: String
    ) {
        clauses.add(column + operator + "?")
        args.add(value)
    }
    
    fun equals(column: String, value: String) = condition(column, "=", value)
    fun equals(column: String, value: Int) = condition(column, "=", value.toString())
    fun equals(column: String, value: Long) = condition(column, "=", value.toString())
    fun equals(column: String, value: Boolean) = condition(column, "=", if (value) "1" else "0")
    fun equals(column: String, value: Float) = condition(column, "=", value.toString())
    
    fun greaterThan(column: String, value: String) = condition(column, ">", value)
    fun greaterThan(column: String, value: Int) = condition(column, ">", value.toString())
    fun greaterThan(column: String, value: Long) = condition(column, ">", value.toString())
    fun greaterThan(column: String, value: Float) = condition(column, ">", value.toString())
    
    fun greaterOrEq(column: String, value: String) = condition(column, ">=", value)
    fun greaterOrEq(column: String, value: Int) = condition(column, ">=", value.toString())
    fun greaterOrEq(column: String, value: Long) = condition(column, ">=", value.toString())
    fun greaterOrEq(column: String, value: Float) = condition(column, ">=", value.toString())
    
    fun lessThan(column: String, value: String) = condition(column, "<", value)
    fun lessThan(column: String, value: Int) = condition(column, "<", value.toString())
    fun lessThan(column: String, value: Long) = condition(column, "<", value.toString())
    fun lessThan(column: String, value: Float) = condition(column, "<", value.toString())
    
    fun lessOrEq(column: String, value: String) = condition(column, "<=", value)
    fun lessOrEq(column: String, value: Int) = condition(column, "<=", value.toString())
    fun lessOrEq(column: String, value: Long) = condition(column, "<=", value.toString())
    fun lessOrEq(column: String, value: Float) = condition(column, "<=", value.toString())
    
    
    
    fun group(                                                   whereBuilder: CondBuilder.()->Unit
    ) {
        val innerBuilder = CondBuilder().apply(whereBuilder)
        clauses.add("(" + innerBuilder.clauses.joinToString(" ") + ")")
        args.addAll(innerBuilder.args)
    }

    
    fun and(                                                     whereBuilder: CondBuilder.()->Unit
    ) {
        clauses.add("AND")
        val innerBuilder = CondBuilder().apply(whereBuilder)
        clauses.addAll(innerBuilder.clauses)
        args.addAll(innerBuilder.args)
    }
    fun andGroup(                                                whereBuilder: CondBuilder.()->Unit
    ) {
        clauses.add("AND")
        group(whereBuilder)
    }
    
    fun or(                                                      whereBuilder: CondBuilder.()->Unit
    ) {
        clauses.add("OR")
        val innerBuilder = CondBuilder().apply(whereBuilder)
        clauses.addAll(innerBuilder.clauses)
        args.addAll(innerBuilder.args)
    }
    fun orGroup(                                                 whereBuilder: CondBuilder.()->Unit
    ) {
        clauses.add("OR")
        group(whereBuilder)
    }
}