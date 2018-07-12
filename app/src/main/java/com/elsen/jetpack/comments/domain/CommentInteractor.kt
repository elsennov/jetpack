package com.elsen.jetpack.comments.domain

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.elsen.jetpack.comments.data.CommentRepository
import com.elsen.jetpack.comments.presentation.CommentDisplayable
import io.reactivex.Observable

/**
 * Created by elsennovraditya on 28/06/18
 */
class CommentInteractor(private val commentRepository: CommentRepository,
                        private val commentsPagedListConfig: PagedList.Config,
                        private val commentBoundaryCallback: CommentBoundaryCallback) {

    fun getPagedComments(): Observable<PagedList<CommentDisplayable>> {
        return RxPagedListBuilder<Int, CommentDisplayable>(
            commentRepository.getPagedComments().map { CommentDisplayable(it.id, it.name, it.email, it.body) },
            commentsPagedListConfig
        )
            .setBoundaryCallback(commentBoundaryCallback)
            .buildObservable()
    }

}