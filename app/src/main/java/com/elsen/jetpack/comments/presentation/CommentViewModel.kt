package com.elsen.jetpack.comments.presentation

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
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

}