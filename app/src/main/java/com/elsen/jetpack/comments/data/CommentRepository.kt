package com.elsen.jetpack.comments.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by elsennovraditya on 28/06/18
 */
class CommentRepository(private val commentReactiveStore: CommentReactiveStore,
                        private val commentService: CommentService,
                        private val commentMapper: CommentMapper) {

    fun getAllComments(): Observable<List<Comment>> {
        return commentReactiveStore.getAll()
    }

    fun fetchComments(start: Int,
                      limit: Int): Completable {
        return commentService
            .getComments(start, limit)
            .subscribeOn(Schedulers.io())
            .map { it.data?.comments ?: listOf() }
            .observeOn(Schedulers.computation())
            .flatMapObservable { Observable.fromIterable(it) }
            .map { commentMapper.apply(it) }
            .toList()
            .doOnSuccess { commentReactiveStore.replaceAll(it) }
            .toCompletable()
    }

}