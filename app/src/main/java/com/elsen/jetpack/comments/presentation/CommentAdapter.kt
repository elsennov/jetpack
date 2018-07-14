package com.elsen.jetpack.comments.presentation

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.elsen.jetpack.R

/**
 * Created by elsennovraditya on 03/07/18
 */
class CommentAdapter : PagedListAdapter<CommentDisplayable, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CommentDisplayable>() {
            // Comment details may have changed if reloaded from the database, but ID is fixed.
            override fun areItemsTheSame(oldComment: CommentDisplayable,
                                         newComment: CommentDisplayable): Boolean =
                oldComment.id == newComment.id

            override fun areContentsTheSame(oldComment: CommentDisplayable,
                                            newComment: CommentDisplayable): Boolean =
                oldComment == newComment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_loading) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            object : RecyclerView.ViewHolder(view) {}
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
            CommentViewHolder(view, currentList)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            getItem(position)?.let { (holder as CommentViewHolder).bind(getItem(position)) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item == null) R.layout.item_loading else R.layout.item_comment
    }

    fun getLastItem(): CommentDisplayable? {
        return currentList?.lastOrNull()
    }

}