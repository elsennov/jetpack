package com.elsen.jetpack.comments.data

import com.elsen.jetpack.base.data.server.ServerResponseWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by elsennovraditya on 28/06/18
 */
interface CommentService {

    @GET("comments")
    fun getComments(@Path("_start") start: Int,
                    @Path("_limit") limit: Int): Single<ServerResponseWrapper<CommentResponse>>

}