package com.elsen.jetpack.base.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.elsen.jetpack.comments.data.CommentDao
import com.elsen.jetpack.comments.data.CommentDb

/**
 * Created by elsennovraditya on 01/07/18
 */
@Database(entities = [CommentDb::class], version = 1)
abstract class JetpackDatabase : RoomDatabase() {

    abstract fun commentDao(): CommentDao

}