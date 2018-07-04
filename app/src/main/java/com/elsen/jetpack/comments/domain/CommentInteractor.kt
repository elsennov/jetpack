package com.elsen.jetpack.comments.domain

import com.elsen.jetpack.comments.data.CommentRepository

/**
 * Created by elsennovraditya on 28/06/18
 */
class CommentInteractor(private val commentRepository: CommentRepository) {

    fun getComments(start: Int, limit: Int) {
        return commentRepository.getAllComments()
    }

}