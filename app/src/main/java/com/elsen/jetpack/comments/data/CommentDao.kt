package com.elsen.jetpack.comments.data

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Created by elsennovraditya on 01/07/18
 */
@Dao
interface CommentDao {

    @Query("SELECT * FROM comment")
    fun getAllComments(): Flowable<List<CommentDb>>

    @Query("SELECT * FROM comment")
    fun getPagedComments(): DataSource.Factory<Int, CommentDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllComments(comments: List<CommentDb>)


}