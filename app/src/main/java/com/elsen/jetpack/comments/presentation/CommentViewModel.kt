package com.elsen.jetpack.comments.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.elsen.jetpack.comments.data.CommentRaw

/**
 * Created by elsennovraditya on 23/06/18
 */
class CommentViewModel(private val comments: MutableLiveData<List<CommentRaw>>) : ViewModel() {

    fun setComments(comments: List<CommentRaw>) {
        this.comments.value = comments
    }

    fun getComments(): MutableLiveData<List<CommentRaw>> {
        if (comments.value?.isEmpty() != false) {
            loadComments()
        }
        return comments
    }

    private fun loadComments() {

    }

}