package com.elsen.jetpack.comments.data

import io.reactivex.functions.Function

/**
 * Created by elsennovraditya on 28/06/18
 */
class CommentDbMapper : Function<Comment, CommentDb> {

    /**
     * Transform comment to be saved in local Db.
     */
    override fun apply(comment: Comment): CommentDb {
        return CommentDb(
            comment.id,
            comment.name,
            comment.email,
            comment.body
        )
    }

}