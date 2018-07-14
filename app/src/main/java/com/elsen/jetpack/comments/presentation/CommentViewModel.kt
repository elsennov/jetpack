package com.elsen.jetpack.comments.presentation

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.elsen.jetpack.base.data.server.InitialLoadErrorEvent
import com.elsen.jetpack.base.data.server.LoadMoreErrorEvent
import com.elsen.jetpack.comments.domain.CommentInteractor
import io.reactivex.Observable
import org.jetbrains.anko.AnkoLogger

/**
 * Created by elsennovraditya on 23/06/18
 */
class CommentViewModel(private val commentInteractor: CommentInteractor) : ViewModel(), AnkoLogger {

    fun getComments(): Observable<PagedList<CommentDisplayable>> {
        return commentInteractor.getPagedComments()
    }

    fun observeInitialLoadErrorEvent(): Observable<InitialLoadErrorEvent> {
        return commentInteractor.observeInitialLoadErrorEvent()
    }

    fun observeLoadMoreErrorEvent(): Observable<LoadMoreErrorEvent> {
        return commentInteractor.observeLoadMoreErrorEvent()
    }

    fun retryLoadMoreComments(lastItem: CommentDisplayable?) {
        commentInteractor.retryLoadMoreComments(lastItem)
    }

}