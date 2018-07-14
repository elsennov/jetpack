package com.elsen.jetpack.comments.domain

import android.arch.paging.PagedList
import com.elsen.jetpack.base.Constant
import com.elsen.jetpack.base.data.server.InitialLoadErrorEvent
import com.elsen.jetpack.base.data.server.LoadMoreErrorEvent
import com.elsen.jetpack.base.rx.RxBus
import com.elsen.jetpack.comments.data.CommentRepository
import com.elsen.jetpack.comments.presentation.CommentDisplayable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class CommentBoundaryCallback(private val commentRepository: CommentRepository) : PagedList.BoundaryCallback<CommentDisplayable>(), AnkoLogger {

    private var requesting = false
    private var start = 0 // First time offset based on initial size

    /**
     * No list from local. We need to get it from server.
     */
    override fun onZeroItemsLoaded() {
        info { "onZeroItemsLoaded" }
        fetchInitialComments()
    }

    private fun fetchInitialComments() {
        if (requesting) return

        requesting = true
        commentRepository
            .fetchComments(start, Constant.PAGE_SIZE)
            .subscribe(
                {
                    info { "onComplete" }
                    start += Constant.PAGE_SIZE
                    requesting = false
                },
                {
                    error("onError", it)
                    requesting = false
                    RxBus.post(InitialLoadErrorEvent())
                }
            )
    }

    /**
     * We reach the end of the list. There is nothing we could retrieve from local. So, we need
     * to get it from server.
     */
    override fun onItemAtEndLoaded(itemAtEnd: CommentDisplayable) {
        info { "onItemAtEndLoaded" }
        fetchMoreComments(itemAtEnd.id.toInt())
    }

    private fun fetchMoreComments(lastIndex: Int) {
        if (requesting) return

        requesting = true
        start = lastIndex

        commentRepository
            .fetchComments(start, Constant.PAGE_SIZE)
            .subscribe(
                {
                    info { "onComplete" }
                    start += Constant.PAGE_SIZE
                    requesting = false
                },
                {
                    error("onError", it)
                    requesting = false
                    RxBus.post(LoadMoreErrorEvent())
                }
            )
    }

}