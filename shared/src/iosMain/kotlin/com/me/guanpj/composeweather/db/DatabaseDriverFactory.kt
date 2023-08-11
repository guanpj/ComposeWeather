package com.me.guanpj.composeweather.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, dbName: String): SqlDriver {
    return NativeSqliteDriver(schema, dbName)
}
