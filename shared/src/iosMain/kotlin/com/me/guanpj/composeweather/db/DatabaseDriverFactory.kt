package com.me.guanpj.composeweather.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual fun createDriver(schema: SqlDriver.Schema, dbName: String): SqlDriver {
    return NativeSqliteDriver(schema, dbName)
}