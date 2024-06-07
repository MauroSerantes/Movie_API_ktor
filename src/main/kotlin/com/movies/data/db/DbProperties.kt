package com.movies.data.db

import java.io.File
import java.io.FileInputStream
import java.util.Properties

object DbProperties {
    fun properties():Properties{
        return Properties().apply {
            load(FileInputStream(File("C:\\Users\\Usuario\\Desktop\\Downloads\\MovieApi\\src\\main\\resources\\db.properties")))
        }
    }
}