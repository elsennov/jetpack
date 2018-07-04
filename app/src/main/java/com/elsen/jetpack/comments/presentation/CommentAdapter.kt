package com.elsen.jetpack.comments.presentation

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by elsennovraditya on 03/07/18
 */
class CommentAdapter : PagedListAdapter<CommentDisplayable, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CommentDisplayable>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldComment: CommentDisplayable,
                                         newComment: CommentDisplayable): Boolean =
                oldComment.id == newComment.id

            override fun areContentsTheSame(oldComment: CommentDisplayable,
                                            newComment: CommentDisplayable): Boolean =
                oldComment == newComment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

}