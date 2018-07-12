package com.elsen.jetpack.comments.domain

import android.arch.paging.PagedList
import com.elsen.jetpack.comments.data.CommentRepository
import com.elsen.jetpack.comments.presentation.CommentDisplayable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class CommentBoundaryCallback(private val commentRepository: CommentRepository) : PagedList.BoundaryCallback<CommentDisplayable>(), AnkoLogger {

    companion object {
        private const val LIMIT = 20
    }

    private var requesting = false
    private var start = 0 // First time offset based on initial size

    /**
     * No more list from local. We need to get it from server.
     */
    override fun onZeroItemsLoaded() {
        info { "onZeroItemsLoaded" }
        fetchMoreComments(start)
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
            .fetchComments(start, LIMIT)
            .subscribe(
                {
                    info { "onComplete" }
                    start += LIMIT
                    requesting = false
                },
                {
                    error("onError", it)
                    requesting = false
                }
            )
    }

}