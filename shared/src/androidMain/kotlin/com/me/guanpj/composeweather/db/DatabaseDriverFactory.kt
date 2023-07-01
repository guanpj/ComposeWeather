package com.me.guanpj.composeweather.db

import com.blankj.utilcode.util.Utils
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual fun createDriver(schema: SqlDriver.Schema, dbName: String): SqlDriver {
    return AndroidSqliteDriver(schema, Utils.getApp(), dbName)
}