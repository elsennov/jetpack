package com.elsen.jetpack.comments.data

import java.io.Serializable

/**
 * Created by elsennovraditya on 23/06/18
 */
data class CommentRaw(val id: String?,
                      val name: String?,
                      val email: String?,
                      val body: String?) : Serializable