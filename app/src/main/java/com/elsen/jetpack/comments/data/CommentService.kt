package com.elsen.jetpack.comments.data

import com.elsen.jetpack.base.data.server.ServerResponseWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by elsennovraditya on 28/06/18
 */
interface CommentService {

    @GET("comments")
    fun getAllComments(): Single<ServerResponseWrapper<CommentResponse>>

    @GET("comments")
    fun getComments(@Query("_start") start: Int,
                    @Query("_limit") limit: Int): Single<List<CommentRaw>>

}