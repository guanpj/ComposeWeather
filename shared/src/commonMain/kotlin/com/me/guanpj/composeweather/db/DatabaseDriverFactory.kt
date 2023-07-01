package com.me.guanpj.composeweather.db

import com.squareup.sqldelight.db.SqlDriver

expect fun createDriver(schema: SqlDriver.Schema, dbName: String): SqlDriver