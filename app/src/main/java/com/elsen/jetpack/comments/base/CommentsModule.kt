package com.elsen.jetpack.comments.base

import android.arch.lifecycle.MutableLiveData
import com.elsen.jetpack.base.data.local.JetpackDatabase
import com.elsen.jetpack.comments.data.*
import com.elsen.jetpack.comments.domain.CommentInteractor
import com.elsen.jetpack.comments.presentation.CommentViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

/**
 * Created by elsennovraditya on 23/06/18
 */
object CommentsModule {

    val module = applicationContext {
        viewModel { CommentViewModel(get()) }
        factory("user_live_data") { MutableLiveData<List<CommentRaw>>() }

        bean { get<JetpackDatabase>("jetpack_database").commentDao() }
        bean { CommentReactiveStore(get()) }
        bean { get<Retrofit>("server_retrofit").create(CommentService::class.java) }
        bean { CommentMapper() }
        bean { CommentRepository(get(), get(), get()) }
        bean { CommentInteractor(get()) }
    }

}