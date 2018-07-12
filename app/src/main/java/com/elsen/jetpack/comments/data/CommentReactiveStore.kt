package com.elsen.jetpack.comments.data

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import android.arch.paging.RxPagedListBuilder
import com.elsen.jetpack.base.data.ReactiveStore
import com.elsen.jetpack.comments.domain.CommentBoundaryCallback
import com.elsen.jetpack.comments.domain.CommentInteractor
import com.elsen.jetpack.comments.presentation.CommentDisplayable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by elsennovraditya on 28/06/18
 */
class CommentReactiveStore(private val commentDao: CommentDao) : ReactiveStore<Comment>, PositionalDataSource<Comment>() {

    companion object {
        private val TAG = CommentReactiveStore::class.java.simpleName
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

    override fun getPaged(): DataSource.Factory<Int, Comment> {
        return commentDao
            .getPagedComments()
            .map { Comment(it.id, it.name, it.email, it.body) }
    }

    override fun getSingle(): Observable<Comment> {
        return Observable.just(Comment.empty)
    }

    override fun storeAll(t: List<Comment>) {
        val commentDbs = t.map { CommentDb(it.id, it.name, it.email, it.body) }
        commentDao.addAllComments(commentDbs)
    }

    override fun storeSingle(t: Comment) {

    }

    override fun replaceAll(t: List<Comment>) {

    }

    override fun replaceSingle(t: Comment) {

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Comment>) {

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Comment>) {

    }

}