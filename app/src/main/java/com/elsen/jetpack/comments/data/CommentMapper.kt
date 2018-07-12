package com.elsen.jetpack.comments.data

import io.reactivex.functions.Function

/**
 * Created by elsennovraditya on 28/06/18
 */
class CommentMapper : Function<CommentRaw, Comment> {

    /**
     * Transform raw model from server for our usage. This is useful if we need to transform value
     * type from backend to our own type (for example enum).
     */
    override fun apply(commentRaw: CommentRaw): Comment {
        return Comment(
            commentRaw.id ?: "",
            commentRaw.name ?: "",
            commentRaw.email ?: "",
            commentRaw.body ?: ""
        )
    }

}