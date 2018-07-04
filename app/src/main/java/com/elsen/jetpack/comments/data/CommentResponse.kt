package com.elsen.jetpack.comments.data

import java.io.Serializable

/**
 * Created by elsennovraditya on 23/06/18
 */
data class CommentResponse(val comments: List<CommentRaw>) : Serializable