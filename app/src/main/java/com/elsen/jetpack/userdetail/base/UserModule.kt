package com.elsen.jetpack.userdetail.base

import android.arch.lifecycle.MutableLiveData
import com.elsen.jetpack.userdetail.presentation.UserDisplayable
import com.elsen.jetpack.userdetail.presentation.UserViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
 * Created by elsennovraditya on 23/06/18
 */
object UserModule {

    val module = applicationContext {
        viewModel { UserViewModel(get()) }
        factory("user_live_data") { MutableLiveData<UserDisplayable>() }
    }

}