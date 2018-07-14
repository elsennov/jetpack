package com.elsen.jetpack.comments.base

import android.arch.paging.PagedList
import com.elsen.jetpack.base.Constant
import com.elsen.jetpack.base.data.local.JetpackDatabase
import com.elsen.jetpack.comments.data.CommentMapper
import com.elsen.jetpack.comments.data.CommentReactiveStore
import com.elsen.jetpack.comments.data.CommentRepository
import com.elsen.jetpack.comments.data.CommentService
import com.elsen.jetpack.comments.domain.CommentBoundaryCallback
import com.elsen.jetpack.comments.domain.CommentInteractor
import com.elsen.jetpack.comments.presentation.CommentViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

/**
 * Created by elsennovraditya on 23/06/18
 */
object CommentModule {

    val module = applicationContext {
        viewModel { CommentViewModel(get()) }

        bean { get<JetpackDatabase>("jetpack_database").commentDao() }
        bean { CommentReactiveStore(get()) }
        bean { get<Retrofit>("server_retrofit").create(CommentService::class.java) }
        bean { CommentMapper() }
        bean { CommentRepository(get(), get(), get()) }
        bean { CommentBoundaryCallback(get()) }
        bean("comments_paged_list_config") {
            PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Constant.PAGE_SIZE)
                .setPrefetchDistance(Constant.PREFETCH_DISTANCE)
                .setPageSize(Constant.PAGE_SIZE)
                .build()
        }
        bean { CommentInteractor(get(), get(), get()) }
    }

}