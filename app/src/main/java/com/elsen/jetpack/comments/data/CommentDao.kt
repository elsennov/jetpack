package com.elsen.jetpack.comments.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Created by elsennovraditya on 01/07/18
 */
@Dao
interface CommentDao {

    @Query("SELECT * FROM comment")
    fun getAllComments(): Flowable<List<CommentDb>>


}