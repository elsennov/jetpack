package com.elsen.jetpack.base.data.local

import android.arch.persistence.room.Room
import org.koin.dsl.module.applicationContext


/**
 * Created by elsennovraditya on 03/07/18
 */
class LocalDbModule {

    companion object {
        val module = applicationContext {
            bean("jetpack_database") {
                Room.databaseBuilder(
                    get("application_context"),
                    JetpackDatabase::class.java,
                    "database-name"
                ).build()
            }
        }
    }

}