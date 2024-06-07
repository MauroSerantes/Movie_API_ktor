package com.movies.data.db

import com.movies.data.db.Schema.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {

    fun init(){
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(MovieDataTable)
            SchemaUtils.create(MovieSynopsisTable)
            SchemaUtils.create(MovieFramesTable)
            SchemaUtils.create(MovieTitlesTable)
            SchemaUtils.create(MovieVideosTable)
        }
    }

    private fun hikari():HikariDataSource{
        val config = HikariConfig()
        config.driverClassName = DbProperties.properties().getProperty("driverClassName")
        config.jdbcUrl = DbProperties.properties().getProperty("jdbcUrl")
        config.username = DbProperties.properties().getProperty("username")
        config.password = DbProperties.properties().getProperty("password")
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T):T = withContext(Dispatchers.IO){
        transaction {
            block()
        }
    }
}