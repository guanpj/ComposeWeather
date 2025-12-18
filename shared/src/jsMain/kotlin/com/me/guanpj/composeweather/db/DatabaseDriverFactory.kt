package com.me.guanpj.composeweather.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlPreparedStatement
import app.cash.sqldelight.db.SqlSchema

// Web 平台简化实现：返回空驱动
// Cache 功能在 Web 版本中不可用（查询总是返回 null），但 Net 功能正常工作
actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, dbName: String): SqlDriver {
    return JsEmptySqlDriver()
}

// 空驱动实现：所有操作都是无操作，查询返回空结果
private class JsEmptySqlDriver : SqlDriver {
    private val noopTransaction = object : Transacter.Transaction() {
        override val enclosingTransaction: Transacter.Transaction? = null

        override fun endTransaction(successful: Boolean): QueryResult.Value<Unit> {
            return QueryResult.Value(Unit)
        }
    }

    override fun close() {}

    override fun currentTransaction(): Transacter.Transaction? = null

    override fun execute(
        identifier: Int?,
        sql: String,
        parameters: Int,
        binders: (SqlPreparedStatement.() -> Unit)?
    ): QueryResult.Value<Long> {
        return QueryResult.Value(0L)
    }

    override fun <R> executeQuery(
        identifier: Int?,
        sql: String,
        mapper: (SqlCursor) -> QueryResult<R>,
        parameters: Int,
        binders: (SqlPreparedStatement.() -> Unit)?
    ): QueryResult<R> {
        // 返回空游标，让查询结果为 null
        return mapper(EmptySqlCursor)
    }

    override fun newTransaction(): QueryResult.Value<Transacter.Transaction> {
        return QueryResult.Value(noopTransaction)
    }

    override fun addListener(vararg queryKeys: String, listener: Query.Listener) {}

    override fun removeListener(vararg queryKeys: String, listener: Query.Listener) {}

    override fun notifyListeners(vararg queryKeys: String) {}
}

// 空游标：next() 总是返回 false
private object EmptySqlCursor : SqlCursor {
    override fun getString(index: Int): String? = null
    override fun getLong(index: Int): Long? = null
    override fun getBytes(index: Int): ByteArray? = null
    override fun getDouble(index: Int): Double? = null
    override fun getBoolean(index: Int): Boolean? = null
    override fun next(): QueryResult.Value<Boolean> = QueryResult.Value(false)
}
