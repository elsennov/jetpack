package com.elsen.jetpack.comments.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by elsennovraditya on 01/07/18
 */
@Entity(tableName = "comment")
data class CommentDb(@PrimaryKey val id: String,
                     @ColumnInfo val name: String,
                     @ColumnInfo val email: String,
                     @ColumnInfo val body: String)