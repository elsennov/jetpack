package com.elsen.jetpack.comments.data

/**
 * Created by elsennovraditya on 28/06/18
 */
data class Comment(val id: String,
                   val name: String,
                   val email: String,
                   val body: String) {

    companion object {
        val empty = Comment("", "", "", "")
    }

}
