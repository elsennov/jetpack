package com.elsen.jetpack.base.presentation

import android.support.v7.widget.RecyclerView

fun <T> RecyclerView.getAdapter(): T {
    return adapter as T
}