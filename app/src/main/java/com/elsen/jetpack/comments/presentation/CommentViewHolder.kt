package com.elsen.jetpack.comments.presentation

import android.arch.paging.PagedList
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Created by elsennovraditya on 03/07/18
 */
class CommentViewHolder(view: View, currentList: PagedList<CommentDisplayable>?) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener {
            val item = currentList?.get(adapterPosition)
            item?.let { Toast.makeText(itemView.context, it.name, Toast.LENGTH_SHORT).show() }
        }
    }

    fun bind(item: CommentDisplayable?) {
        itemView.comment_name.text = item?.name ?: ""
        itemView.comment_email.text = item?.email ?: ""
        itemView.comment_body.text = item?.body ?: ""
    }

}