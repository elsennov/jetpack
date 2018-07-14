package com.elsen.jetpack.comments.data

import android.arch.paging.DataSource
import com.elsen.jetpack.base.rx.toSpecificErrorIfAny
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

    fun fetchAllComments(): Completable {
        return commentService
            .getAllComments()
            .subscribeOn(Schedulers.io())
            .map { it.data?.comments ?: listOf() }
            .observeOn(Schedulers.computation())
            .flatMapObservable { Observable.fromIterable(it) }
            .map { commentMapper.apply(it) }
            .toList()
            .doOnSuccess { commentReactiveStore.replaceAll(it) }
            .toCompletable()
    }

    fun getPagedComments(): DataSource.Factory<Int, Comment> {
        return commentReactiveStore.getPaged()
    }

    fun fetchComments(start: Int,
                      limit: Int): Completable {
        return commentService
            .getComments(start, limit)
            .toSpecificErrorIfAny()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .flatMapObservable { Observable.fromIterable(it) }
            .map { commentMapper.apply(it) }
            .toList()
            .doOnSuccess { commentReactiveStore.storeAll(it) }
            .toCompletable()
    }

}