package com.elsen.jetpack.comments.data

import com.elsen.jetpack.base.data.ReactiveStore
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by elsennovraditya on 28/06/18
 */
class CommentReactiveStore(private val commentDao: CommentDao) : ReactiveStore<Comment> {

    companion object {
        private val TAG = CommentReactiveStore::class.java.simpleName
    }

    override fun getSingle(): Observable<Comment> {
        return Observable.just(Comment.empty)
    }

    override fun storeSingle(t: Comment) {

    }

    override fun getAll(): Observable<List<Comment>> {
        return commentDao
            .getAllComments()
            .toObservable()
            .flatMap {
                Observable
                    .fromIterable(it)
                    .observeOn(Schedulers.computation())
                    .map { Comment(it.id, it.name, it.email, it.body) }
            }
            .toList()
            .toObservable()
    }

    override fun storeAll(t: List<Comment>) {

    }

    override fun replaceAll(t: List<Comment>) {

    }

}